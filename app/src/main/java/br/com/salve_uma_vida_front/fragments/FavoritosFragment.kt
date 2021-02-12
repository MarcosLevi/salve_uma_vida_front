package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.CardOngFavoritaAdapter
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.databinding.FragmentBothFavoritosBinding
import br.com.salve_uma_vida_front.dto.OngFavoritaDto
import br.com.salve_uma_vida_front.startLoading
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewholders.CardOngFavoritaViewHolder
import br.com.salve_uma_vida_front.viewmodels.UserViewModel

class FavoritosFragment : Fragment() {

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
        startLoading(requireActivity(), R.id.mainLoading)
        configuraRecyclerView()
        configuraToolbar()
        configuraObservers()
        carregaOngsFavoritas()
        super.onViewCreated(view, savedInstanceState)
    }

    fun carregaOngsFavoritas(){
        viewModel.getOngsFavororitasDoUserLogado()
    }

    private fun configuraObservers() {
        viewModel.ongsFavoritasDoUserLogado.observe(viewLifecycleOwner, Observer {
            listaOngsFavoritas.clear()
            if (it != null) {
                listaOngsFavoritas.addAll(it)
            }
            mRecyclerView.adapter!!.notifyDataSetChanged()
            closeLoading(requireActivity(), R.id.mainLoading)
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
                requireContext()
            )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = ongFavoritaAdapter
    }
}