package br.com.salve_uma_vida_front.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.*
import br.com.salve_uma_vida_front.adapters.CardCampanhaEditavelAdapter
import br.com.salve_uma_vida_front.adapters.CardEventoEditavelAdapter
import br.com.salve_uma_vida_front.databinding.FragmentOngCampanhasBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import br.com.salve_uma_vida_front.interfaces.CardCampanhaEditavelListener
import br.com.salve_uma_vida_front.interfaces.CardEventoEditavelListener
import br.com.salve_uma_vida_front.models.DialogFiltros
import br.com.salve_uma_vida_front.models.SearchType
import br.com.salve_uma_vida_front.viewholders.CardCampanhaEditavelViewHolder
import br.com.salve_uma_vida_front.viewholders.CardEventoEditavelViewHolder
import br.com.salve_uma_vida_front.viewmodels.CampanhasViewModel
import br.com.salve_uma_vida_front.viewmodels.EventosViewModel


class MinhasCampanhasEventosFragment : Fragment(), DialogFiltros.DialogFiltroListener, CardEventoEditavelListener, CardCampanhaEditavelListener {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var campanhaEditavelAdapter: RecyclerView.Adapter<CardCampanhaEditavelViewHolder>
    lateinit var eventoEditavelAdapter: RecyclerView.Adapter<CardEventoEditavelViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentOngCampanhasBinding
    private lateinit var viewModelCampanha: CampanhasViewModel
    private lateinit var viewModelEvento: EventosViewModel
    private val listaEventos: MutableList<EventoDto> = mutableListOf()
    private val listaCampanhas: MutableList<CampanhaDto> = mutableListOf()
    private var filtroAtual: FiltroPesquisaDto = FiltroPesquisaDto()

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    private val fromBotton: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_botton_anim
        )
    }
    private val toBotton: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.to_bottom_anim
        )
    }

    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOngCampanhasBinding.inflate(inflater, container, false)
        viewModelCampanha = ViewModelProviders.of(this).get(CampanhasViewModel::class.java)
        viewModelEvento = ViewModelProviders.of(this).get(EventosViewModel::class.java)
        configuraToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        configuraRecyclerView()
        configuraObservers()
        carregaDados()
        configuraFabs()
        setHasOptionsMenu(true)

    }

    private fun openDialog() {

        val dialog = DialogFiltros(this)
        dialog.show(childFragmentManager, "FiltroDialog")

    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.inflateMenu(R.menu.fragment_procurar_menu)
        configuraSearchView(toolbar)
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.procurarFragmentPesquisar -> {
                    val searchView = it.actionView as SearchView
                    searchView.requestFocusFromTouch()
                    true
                }

                R.id.procurarFragmentFiltros -> {
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
        val procurar = toolbar?.menu?.findItem(R.id.procurarFragmentPesquisar)
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

    private fun configuraFabs() {
        binding.ongCampanhasFragmentAdd.setOnClickListener {
            onAddButtonClicked()
        }
        binding.ongCampanhasFragmentFabAddCampanha.setOnClickListener {
            clicked = !clicked
            navController!!.navigate(R.id.action_ongCampanhasFragment_to_cadastroCampanhaFragment)
        }
        binding.ongCampanhasFragmentFabAddEvento.setOnClickListener {
            clicked = !clicked
            navController!!.navigate(MinhasCampanhasEventosFragmentDirections.actionOngCampanhasFragmentToCadastroEventoFragment())

        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            expandeAdicionar()
        } else {
            fechaAdicionar()
        }
    }

    private fun fechaAdicionar() {
        binding.ongCampanhasFragmentFabAddEvento.startAnimation(toBotton)
        binding.ongCampanhasFragmentFabAddCampanha.startAnimation(toBotton)
        binding.ongCampanhasFragmentLabelCampanha.startAnimation(toBotton)
        binding.ongCampanhasFragmentLabelEvento.startAnimation(toBotton)
        binding.ongCampanhasFragmentAdd.startAnimation(rotateClose)
    }

    private fun expandeAdicionar() {
        binding.ongCampanhasFragmentFabAddEvento.startAnimation(fromBotton)
        binding.ongCampanhasFragmentFabAddCampanha.startAnimation(fromBotton)
        binding.ongCampanhasFragmentLabelCampanha.startAnimation(fromBotton)
        binding.ongCampanhasFragmentLabelEvento.startAnimation(fromBotton)
        binding.ongCampanhasFragmentAdd.startAnimation(rotateOpen)
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.ongCampanhasFragmentFabAddEvento.visibility = View.VISIBLE
            binding.ongCampanhasFragmentFabAddCampanha.visibility = View.VISIBLE
            binding.ongCampanhasFragmentLabelCampanha.visibility = View.VISIBLE
            binding.ongCampanhasFragmentLabelEvento.visibility = View.VISIBLE
        } else {
            binding.ongCampanhasFragmentFabAddEvento.visibility = View.INVISIBLE
            binding.ongCampanhasFragmentFabAddCampanha.visibility = View.INVISIBLE
            binding.ongCampanhasFragmentLabelCampanha.visibility = View.INVISIBLE
            binding.ongCampanhasFragmentLabelEvento.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.ongCampanhasFragmentFabAddEvento.isClickable = true
            binding.ongCampanhasFragmentFabAddCampanha.isClickable = true
        } else {
            binding.ongCampanhasFragmentFabAddEvento.isClickable = false
            binding.ongCampanhasFragmentFabAddCampanha.isClickable = false
        }
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        campanhaEditavelAdapter =
            CardCampanhaEditavelAdapter(
                listaCampanhas,
                this
            )
        eventoEditavelAdapter =
            CardEventoEditavelAdapter(
                listaEventos,
                this
            )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = campanhaEditavelAdapter
    }

    private fun configuraObservers() {
        viewModelCampanha.minhasCampanhas.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                listaCampanhas.addAll(it)
            }
            campanhaEditavelAdapter.notifyDataSetChanged()
        })
        viewModelEvento.meusEventos.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                listaEventos.addAll(it)
            }
            eventoEditavelAdapter.notifyDataSetChanged()
        })
        viewModelCampanha.closeCampanha.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showText(requireContext(),"Campanha fechada")
            }
            carregaDados()
        })
        viewModelEvento.closeEvento.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showText(requireContext(),"Evento fechado")
            }
            carregaDados()
        })
    }

    private fun carregaCampanhasUserLogado(parametro: String) {
        viewModelCampanha.getCampanhasUserLogado(parametro)
    }

    private fun carregaEventosUserLogado(parametro: String) {
        viewModelEvento.getEventosUserLogado(parametro)
    }

    private fun carregaDados(parametro: String = "") {
        val toolbar = getToolbar(activity)!!
        view?.hideKeyboard()
        if (filtroAtual.tipoFiltro == SearchType.CAMPANHAS) {
            setaAdapterCampanhas()
            toolbar.title = "Minhas Campanhas"
            toolbar.setBackgroundColor(resources.getColor(R.color.corCampanhas))
            carregaCampanhasUserLogado(parametro)
        } else if (filtroAtual.tipoFiltro == SearchType.EVENTOS) {
            setaAdapterEventos()
            toolbar.title = "Meus Eventos"
            toolbar.setBackgroundColor(resources.getColor(R.color.corEventos))
            carregaEventosUserLogado(parametro)
        }
    }

    private fun setaAdapterEventos() {
        mRecyclerView.adapter = eventoEditavelAdapter
        listaEventos.clear()
        eventoEditavelAdapter.notifyDataSetChanged()
    }

    private fun setaAdapterCampanhas() {
        mRecyclerView.adapter = campanhaEditavelAdapter
        listaCampanhas.clear()
        campanhaEditavelAdapter.notifyDataSetChanged()
    }

    override fun passaFiltro(filtro: FiltroPesquisaDto) {
        filtroAtual = filtro
        carregaDados()
    }

    private fun showDialog(evento: EventoDto? = null, campanha: CampanhaDto? = null){
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(requireContext(),R.style.DialogTheme)
        builder.setTitle(if (evento!=null)"Arquivar Evento" else "Arquivar Campanha")
        builder.setMessage(if (evento!=null) "Deseja arquivar o evento? (Os doadores não poderão ver o evento quando arquivado)" else "Deseja arquivar a campanha? (Os doadores não poderão ver a campanha quando arquivada)" )
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
//                    viewModelCampanha.arquivaCampanha()
                    if (evento != null)
                        viewModelEvento.closeEventoId(evento.id!!)
                    else if (campanha!= null)
                        viewModelCampanha.closeCampanhaId(campanha.id!!)
                }
            }
        }
        builder.setPositiveButton("SIM",dialogClickListener)
        builder.setNegativeButton("NÃO",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }

    override fun onArquivaClicked(evento: EventoDto) {
        showDialog(evento,null)
    }

    override fun onArquivaClicked(campanha: CampanhaDto) {
        showDialog(null, campanha)
    }
}

