package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.models.Variaveis
import br.com.salve_uma_vida_front.adapters.CardCampanhaFinalAdapter
import br.com.salve_uma_vida_front.adapters.CardEventoFinalAdapter
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.viewholders.CardCampanhaFinalViewHolder
import br.com.salve_uma_vida_front.viewholders.CardEventoFinalViewHolder
import br.com.salve_uma_vida_front.viewmodels.CampanhasEEventosViewModel
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.hideKeyboard
import br.com.salve_uma_vida_front.startLoading

class ProcurarFragment : Fragment() {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var campanhaFinalAdapter: RecyclerView.Adapter<CardCampanhaFinalViewHolder>
    lateinit var eventoFinalAdapter: RecyclerView.Adapter<CardEventoFinalViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothProcurarBinding
    private lateinit var viewModel: CampanhasEEventosViewModel
    private val listaEventos: MutableList<EventoDto> = mutableListOf()
    private val listaCampanhas: MutableList<CampanhaDto> = mutableListOf()
    private var filtroAtual: String = Variaveis().CAMPANHAS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBothProcurarBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(CampanhasEEventosViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        configuraRecyclerView()
        configuraObservers()
        carregaCampanhas()
        setHasOptionsMenu(true)
        val toolbar = activity?.findViewById<Toolbar>(R.id.ongToolbar)
        toolbar?.menu?.clear()
        toolbar?.inflateMenu(R.menu.fragment_both_procurar_menu)
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        campanhaFinalAdapter =
            CardCampanhaFinalAdapter(
                listaCampanhas,
                requireContext()
            )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = campanhaFinalAdapter
    }

    private fun configuraObservers() {
        viewModel.campanhas.observe(viewLifecycleOwner, Observer {
            listaCampanhas.clear()
            if (it != null) {
                listaCampanhas.addAll(it)
            }
            campanhaFinalAdapter =
                CardCampanhaFinalAdapter(
                    listaCampanhas,
                    requireContext()
                )
            mRecyclerView.adapter = campanhaFinalAdapter
            closeLoading(parentFragmentManager)
            view?.hideKeyboard()
        })
        viewModel.eventos.observe(viewLifecycleOwner, Observer {
            listaEventos.clear()
            if (it != null) {
                listaEventos.addAll(it)
            }
            eventoFinalAdapter =
                CardEventoFinalAdapter(
                    listaEventos,
                    requireContext()
                )
            mRecyclerView.adapter = eventoFinalAdapter
            closeLoading(parentFragmentManager)
            view?.hideKeyboard()
        })
        viewModel.campanhaOuEvento.observe(viewLifecycleOwner, Observer {
            filtroAtual = it
            if (filtroAtual.equals(Variaveis().CAMPANHAS)) {
                carregaCampanhas()
            } else if (filtroAtual.equals(Variaveis().EVENTOS)) {
                carregaEventos()
            }
        })
    }

    private fun carregaCampanhas(parametro: String = "") {
        startLoading(parentFragmentManager)
        viewModel.getCampanhas(parametro)
    }

    private fun carregaEventos(parametro: String = "") {
        startLoading(parentFragmentManager)
        viewModel.getEventos(parametro)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.fragment_both_procurar_menu, menu)
//        val procurar: MenuItem = menu.findItem(R.id.bothProcurarFragmentPesquisar)
//        val searchView = procurar.actionView as SearchView
//        searchView.isIconified = false
//        procurar.setOnMenuItemClickListener {
//            searchView.requestFocusFromTouch()
//        }
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(textoDeBusca: String): Boolean {
//                if (filtroAtual.equals(Variaveis().CAMPANHAS)) {
//                    carregaCampanhas(textoDeBusca);
//                } else if (filtroAtual.equals(Variaveis().EVENTOS)) {
//                    carregaEventos(textoDeBusca)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(novoTexto: String): Boolean {
//                if (novoTexto == "") {
//                    this.onQueryTextSubmit("");
//                }
//                return true
//            }
//
//        })
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bothProcurarFragmentFiltros -> {
                viewModel.createDialog(fragmentManager)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}