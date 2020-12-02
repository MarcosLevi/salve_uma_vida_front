package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.CardCampanhaEditavelAdapter
import br.com.salve_uma_vida_front.adapters.CardEventoEditavelAdapter
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.databinding.FragmentOngCampanhasBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.models.Variaveis
import br.com.salve_uma_vida_front.startLoading
import br.com.salve_uma_vida_front.viewholders.CardCampanhaEditavelViewHolder
import br.com.salve_uma_vida_front.viewholders.CardEventoEditavelViewHolder
import br.com.salve_uma_vida_front.viewmodels.CampanhasEEventosViewModel


class CampanhasFragment : Fragment() {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var campanhaFinalAdapter: RecyclerView.Adapter<CardCampanhaEditavelViewHolder>
    lateinit var eventoEditavelAdapter: RecyclerView.Adapter<CardEventoEditavelViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentOngCampanhasBinding
    private lateinit var viewModel: CampanhasEEventosViewModel
    private val listaEventos: MutableList<EventoDto> = mutableListOf()
    private val listaCampanhas: MutableList<CampanhaDto> = mutableListOf()
    private var filtroAtual: String = Variaveis().CAMPANHAS

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
        viewModel = ViewModelProviders.of(this).get(CampanhasEEventosViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        configuraRecyclerView()
        configuraObservers()
        carregaCampanhasUserLogado()
        configuraFabs()
        setHasOptionsMenu(true)
    }

    private fun configuraFabs() {
        binding.ongCampanhasFragmentAdd.setOnClickListener {
            onAddButtonClicked()
        }
        binding.ongCampanhasFragmentFabAddCampanha.setOnClickListener {

        }
        binding.ongCampanhasFragmentFabAddEvento.setOnClickListener {

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
            binding.ongCampanhasFragmentFabAddEvento.startAnimation(fromBotton)
            binding.ongCampanhasFragmentFabAddCampanha.startAnimation(fromBotton)
            binding.ongCampanhasFragmentLabelCampanha.startAnimation(fromBotton)
            binding.ongCampanhasFragmentLabelEvento.startAnimation(fromBotton)
            binding.ongCampanhasFragmentAdd.startAnimation(rotateOpen)
        } else {
            binding.ongCampanhasFragmentFabAddEvento.startAnimation(toBotton)
            binding.ongCampanhasFragmentFabAddCampanha.startAnimation(toBotton)
            binding.ongCampanhasFragmentLabelCampanha.startAnimation(toBotton)
            binding.ongCampanhasFragmentLabelEvento.startAnimation(toBotton)
            binding.ongCampanhasFragmentAdd.startAnimation(rotateClose)
        }
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
        campanhaFinalAdapter =
            CardCampanhaEditavelAdapter(
                listaCampanhas,
                requireContext()
            )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = campanhaFinalAdapter
    }

    private fun configuraObservers() {
        viewModel.minhasCampanhas.observe(viewLifecycleOwner, Observer {
            closeLoading(parentFragmentManager)
            listaCampanhas.clear()
            if (it != null) {
                listaCampanhas.addAll(it)
            }
            campanhaFinalAdapter =
                CardCampanhaEditavelAdapter(
                    listaCampanhas,
                    requireContext()
                )
            mRecyclerView.adapter = campanhaFinalAdapter
        })
        viewModel.meusEventos.observe(viewLifecycleOwner, Observer {
            closeLoading(parentFragmentManager)
            listaEventos.clear()
            if (it != null) {
                listaEventos.addAll(it)
            }
            eventoEditavelAdapter =
                CardEventoEditavelAdapter(
                    listaEventos,
                    requireContext()
                )
            mRecyclerView.adapter = eventoEditavelAdapter
        })
        viewModel.campanhaOuEvento.observe(viewLifecycleOwner, Observer {
            filtroAtual = it
            if (filtroAtual.equals(Variaveis().CAMPANHAS)) {
                carregaCampanhasUserLogado()
            } else if (filtroAtual.equals(Variaveis().EVENTOS)) {
                carregaEventosUserLogado()
            }

        })
    }

    private fun carregaCampanhasUserLogado(parametro: String = "") {
        startLoading(parentFragmentManager)
        viewModel.getCampanhasUserLogado(parametro)
    }

    private fun carregaEventosUserLogado(parametro: String = "") {
        startLoading(parentFragmentManager)
        viewModel.getEventosUserLogado(parametro)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_both_procurar_menu, menu)
        var procurar: MenuItem = menu.findItem(R.id.bothProcurarFragmentPesquisar)
        var searchView = procurar.actionView as SearchView

//Precisa do ajuste
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(textoDeBusca: String): Boolean {
                if (filtroAtual.equals(Variaveis().CAMPANHAS)) {
//                    carregaCampanhasUserLogado(textoDeBusca);
                    carregaCampanhasUserLogado(textoDeBusca)
                } else if (filtroAtual.equals(Variaveis().EVENTOS)) {
//                    carregaEventosUserLogado(textoDeBusca)
                    carregaEventosUserLogado(textoDeBusca)
                }
                return true
            }

            override fun onQueryTextChange(novoTexto: String): Boolean {
                if (novoTexto == "") {
                    this.onQueryTextSubmit("");
                }
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bothProcurarFragmentFiltros -> {
                viewModel.createDialog(fragmentManager)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

