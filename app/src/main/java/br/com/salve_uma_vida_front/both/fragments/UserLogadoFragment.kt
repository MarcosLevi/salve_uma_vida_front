package br.com.salve_uma_vida_front.both.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.Variaveis
import br.com.salve_uma_vida_front.both.adapters.CardCampanhaAdapter
import br.com.salve_uma_vida_front.both.adapters.CardEventoAdapter
import br.com.salve_uma_vida_front.both.models.LoadingDialog
import br.com.salve_uma_vida_front.both.viewholders.CardCampanhaViewHolder
import br.com.salve_uma_vida_front.both.viewholders.CardEventoViewHolder
import br.com.salve_uma_vida_front.both.viewmodels.CampanhasEEventosViewModel
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarBinding
import br.com.salve_uma_vida_front.databinding.FragmentPerfilUserLogadoBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto

class UserLogadoFragment : Fragment() {
    var navController: NavController? = null
    lateinit var binding: FragmentPerfilUserLogadoBinding
    private lateinit var myMenu:Menu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilUserLogadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setHasOptionsMenu(true)
    }

    fun startLoading(){
        val loadingDialog = LoadingDialog()
        loadingDialog.show(parentFragmentManager,"Loading")
    }

    fun closeLoading(){
        val transaction = parentFragmentManager.beginTransaction()
        val loadingDialog = parentFragmentManager.findFragmentByTag("Loading") as LoadingDialog
        loadingDialog.dismiss()
        transaction.remove(loadingDialog)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_both_perfil_user_logado_menu, menu)
        myMenu=menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bothPerfilUserLogadoEdita -> {
                item.isVisible = false
                myMenu.findItem(R.id.bothPerfilUserLogadoFinaliza).setVisible(true)

            }
            R.id.bothPerfilUserLogadoFinaliza -> {
                item.isVisible = false
                myMenu.findItem(R.id.bothPerfilUserLogadoEdita).setVisible(true)
            }
        }
        return super.onOptionsItemSelected(item)
    }



}