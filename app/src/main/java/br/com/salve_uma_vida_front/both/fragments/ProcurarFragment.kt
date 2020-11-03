package br.com.salve_uma_vida_front.both.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.Variaveis
import br.com.salve_uma_vida_front.both.adapters.CardCampanhaAdapter
import br.com.salve_uma_vida_front.both.adapters.CardEventoAdapter
import br.com.salve_uma_vida_front.both.viewholders.CardCampanhaViewHolder
import br.com.salve_uma_vida_front.both.viewholders.CardEventoViewHolder
import br.com.salve_uma_vida_front.both.viewmodels.ProcurarFragmentViewModel
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto

class ProcurarFragment : Fragment() {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var campanhaAdapter: RecyclerView.Adapter<CardCampanhaViewHolder>
    lateinit var eventoAdapter: RecyclerView.Adapter<CardEventoViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothProcurarBinding
    private lateinit var viewModel: ProcurarFragmentViewModel
    private val listaEventos: MutableList<EventoDto> = mutableListOf()
    private val listaCampanhas: MutableList<CampanhaDto> = mutableListOf()
    private var filtroAtual: String = Variaveis().CAMPANHAS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBothProcurarBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(ProcurarFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        configuraRecyclerView()
        configuraObservers()
        carregaCampanhas()
        setHasOptionsMenu(true)
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        campanhaAdapter = CardCampanhaAdapter(
            listaCampanhas,
            requireContext()
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = campanhaAdapter
    }

    private fun configuraObservers() {
        viewModel.campanhas.observe(viewLifecycleOwner, Observer {
            listaCampanhas.clear()
            if (it != null) {
                listaCampanhas.addAll(it)
            }
            campanhaAdapter = CardCampanhaAdapter(
                listaCampanhas,
                requireContext()
            )
            mRecyclerView.adapter = campanhaAdapter
        })
        viewModel.eventos.observe(viewLifecycleOwner, Observer {
            listaEventos.clear()
            if (it != null) {
                listaEventos.addAll(it)
            }
            eventoAdapter = CardEventoAdapter(
                listaEventos,
                requireContext()
            )
            mRecyclerView.adapter = eventoAdapter
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
        viewModel.getCampanhas(parametro)
    }

    private fun carregaEventos(parametro: String = "") {
        viewModel.getEventos(parametro)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_both_procurar_menu, menu)
        var procurar: MenuItem = menu.findItem(R.id.bothProcurarFragmentPesquisar)
        var searchView = procurar.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(textoDeBusca: String): Boolean {
                if (filtroAtual.equals(Variaveis().CAMPANHAS)) {
                    carregaCampanhas(textoDeBusca);
                } else if (filtroAtual.equals(Variaveis().EVENTOS)) {
                    carregaEventos(textoDeBusca)
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