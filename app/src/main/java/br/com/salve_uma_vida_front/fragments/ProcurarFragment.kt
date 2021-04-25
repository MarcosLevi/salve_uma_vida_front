package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.CardCampanhaFinalAdapter
import br.com.salve_uma_vida_front.adapters.CardEventoFinalAdapter
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import br.com.salve_uma_vida_front.getToolbar
import br.com.salve_uma_vida_front.hideKeyboard
import br.com.salve_uma_vida_front.interfaces.CardCampanhaFinalListener
import br.com.salve_uma_vida_front.interfaces.CardEventoFinalListener
import br.com.salve_uma_vida_front.models.DialogFiltros
import br.com.salve_uma_vida_front.models.SearchType
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewholders.CardCampanhaFinalViewHolder
import br.com.salve_uma_vida_front.viewholders.CardEventoFinalViewHolder
import br.com.salve_uma_vida_front.viewmodels.CampanhasViewModel
import br.com.salve_uma_vida_front.viewmodels.EventosViewModel


class ProcurarFragment : Fragment(), DialogFiltros.DialogFiltroListener, CardCampanhaFinalListener,
    CardEventoFinalListener {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var campanhaFinalAdapter: RecyclerView.Adapter<CardCampanhaFinalViewHolder>
    lateinit var eventoFinalAdapter: RecyclerView.Adapter<CardEventoFinalViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothProcurarBinding
    private lateinit var viewModelCampanha: CampanhasViewModel
    private lateinit var viewModelEvento: EventosViewModel
    private val listaEventos: MutableList<EventoDto> = mutableListOf()
    private val listaCampanhas: MutableList<CampanhaDto> = mutableListOf()
    private var filtroAtual: FiltroPesquisaDto = FiltroPesquisaDto()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelCampanha = ViewModelProviders.of(this).get(CampanhasViewModel::class.java)
        viewModelEvento = ViewModelProviders.of(this).get(EventosViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBothProcurarBinding.inflate(inflater, container, false)
        configuraObservers()
        configuraToolbar()
        configuraRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carregaDados()
        navController = Navigation.findNavController(view)
    }

    private fun openDialog() {

        val dialog = DialogFiltros(this)
        dialog.show(childFragmentManager, "FiltroDialog")

    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.inflateMenu(R.menu.fragment_both_procurar_menu)
        configuraSearchView(toolbar)
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.bothProcurarFragmentPesquisar -> {
                    val searchView = it.actionView as SearchView
                    searchView.requestFocusFromTouch()
                    true
                }

                R.id.bothProcurarFragmentFiltros -> {
                    openDialog()
                    true
                }
                else -> {
                    throw IllegalArgumentException("Item inexistente")
                }
            }
        }
    }

    private fun configuraSearchView(toolbar: Toolbar?) {
        val procurar = toolbar?.menu?.findItem(R.id.bothProcurarFragmentPesquisar)
        val searchView = procurar?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(textoDeBusca: String): Boolean {
                carregaDados(textoDeBusca)
                return true
            }

            override fun onQueryTextChange(novoTexto: String): Boolean {
                return true
            }

        })
        searchView.setOnCloseListener {
            carregaDados()
            false
        }
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        campanhaFinalAdapter =
            CardCampanhaFinalAdapter(
                listaCampanhas,
                requireContext(),
                this
            )
        eventoFinalAdapter =
            CardEventoFinalAdapter(
                listaEventos,
                requireContext(),
                this
            )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = campanhaFinalAdapter
    }

    private fun configuraObservers() {
        viewModelCampanha.campanhas.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                listaCampanhas.addAll(it)
            }
            campanhaFinalAdapter.notifyDataSetChanged()
        })
        viewModelEvento.eventos.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                listaEventos.addAll(it)
            }
            eventoFinalAdapter.notifyDataSetChanged()
        })
    }

    private fun carregaCampanhas(parametro: String) {
        viewModelCampanha.getCampanhas(parametro)
    }

    private fun carregaEventos(parametro: String) {
        viewModelEvento.getEventos(parametro)
    }

    private fun carregaDados(parametro: String = "") {
        val toolbar = getToolbar(activity)!!
        view?.hideKeyboard()
        if (filtroAtual.tipoFiltro == SearchType.CAMPANHAS) {
            setaAdapterCampanhas()
            toolbar.title = "Procurar Campanhas"
            toolbar.setBackgroundColor(resources.getColor(R.color.corCampanhas))
            carregaCampanhas(parametro)
        } else if (filtroAtual.tipoFiltro == SearchType.EVENTOS) {
            setaAdapterEventos()
            toolbar.title = "Procurar Eventos"
            toolbar.setBackgroundColor(resources.getColor(R.color.corEventos))
            carregaEventos(parametro)
        }
    }

    private fun setaAdapterCampanhas() {
        mRecyclerView.adapter = campanhaFinalAdapter
        listaCampanhas.clear()
        campanhaFinalAdapter.notifyDataSetChanged()
    }

    private fun setaAdapterEventos() {
        mRecyclerView.adapter = eventoFinalAdapter
        listaEventos.clear()
        eventoFinalAdapter.notifyDataSetChanged()
    }

    override fun passaFiltro(filtro: FiltroPesquisaDto) {
        filtroAtual = filtro
        carregaDados()
    }

    override fun abreCampanha(campanha: CampanhaDto) {
        navController!!.navigate(
            ProcurarFragmentDirections.actionBothProcurarFragmentToCampanhaDetalhadaFragment(
                campanha.id!!
            )
        )
    }

    override fun abreEvento(evento: EventoDto) {
        navController!!.navigate(
            ProcurarFragmentDirections.actionBothProcurarFragmentToEventoDetalhadoFragment(
                evento.id!!
            )
        )
    }

    override fun abrePerfilOng(id: Int) {
        navController!!.navigate(
            ProcurarFragmentDirections.actionBothProcurarFragmentToPerfilOngFragment(
                id
            )
        )
    }


}