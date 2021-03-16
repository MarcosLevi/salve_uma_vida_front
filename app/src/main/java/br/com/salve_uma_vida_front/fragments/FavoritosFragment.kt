package br.com.salve_uma_vida_front.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.*
import br.com.salve_uma_vida_front.adapters.CardOngFavoritaAdapter
import br.com.salve_uma_vida_front.databinding.FragmentBothFavoritosBinding
import br.com.salve_uma_vida_front.dto.OngFavoritaDto
import br.com.salve_uma_vida_front.interfaces.CardOngFavoritaListener
import br.com.salve_uma_vida_front.viewholders.CardOngFavoritaViewHolder
import br.com.salve_uma_vida_front.viewmodels.UserViewModel


class FavoritosFragment : Fragment(), CardOngFavoritaListener {

    var navController: NavController? = null
    private lateinit var viewModel: UserViewModel
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothFavoritosBinding
    lateinit var ongFavoritaAdapter: RecyclerView.Adapter<CardOngFavoritaViewHolder>
    private val listaOngsFavoritas: MutableList<OngFavoritaDto> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBothFavoritosBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        configuraRecyclerView()
        configuraToolbar()
        configuraObservers()
        carregaOngsFavoritas()
        super.onViewCreated(view, savedInstanceState)
    }

    fun carregaOngsFavoritas(){
        viewModel.getOngsFavororitasDoUserLogado()
        startLoading(activity, R.id.ongLoading)
    }

    private fun configuraObservers() {
        viewModel.ongsFavoritasDoUserLogado.observe(viewLifecycleOwner, Observer {
            listaOngsFavoritas.clear()
            if (it != null) {
                listaOngsFavoritas.addAll(it)
            }
            mRecyclerView.adapter!!.notifyDataSetChanged()
            closeLoading(activity, R.id.ongLoading)
        })
        viewModel.respostaDoBancoAoDesfavoritar.observe(viewLifecycleOwner, Observer {
            closeLoading(activity,R.id.ongLoading)
            if (it){
                carregaOngsFavoritas()
            }
        })
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsFavoritos
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        ongFavoritaAdapter =
            CardOngFavoritaAdapter(
                listaOngsFavoritas,
                requireContext(),
                this
            )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = ongFavoritaAdapter
    }

    override fun abrePerfilOng(id: Int) {
        navController!!.navigate(FavoritosFragmentDirections.actionBothFavoritosFragmentToPerfilOngFragment(id))
    }

    override fun desfavoritarOngPorId(id: Int) {
        showDialog(id)
    }

    private fun showDialog(id: Int){
        lateinit var dialog:AlertDialog
        val builder = AlertDialog.Builder(requireContext(),R.style.DialogTheme)
        builder.setTitle("Desfavoritar Ong")
        builder.setMessage("Deseja desfavoritar Ong?")
        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.desfavoritarOngPorId(id)
                    startLoading(activity,R.id.ongLoading)
                }
            }
        }
        builder.setPositiveButton("SIM",dialogClickListener)
        builder.setNegativeButton("NÃO",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }
}