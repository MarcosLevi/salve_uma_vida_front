package br.com.salve_uma_vida_front.ongs.fragments

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
import br.com.salve_uma_vida_front.both.models.LoadingDialog
import br.com.salve_uma_vida_front.both.viewholders.CardCampanhaViewHolder
import br.com.salve_uma_vida_front.both.viewholders.CardEventoViewHolder
import br.com.salve_uma_vida_front.both.viewmodels.CampanhasEEventosViewModel
import br.com.salve_uma_vida_front.databinding.FragmentOngCampanhasBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto


class CampanhasFragment : Fragment() {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var campanhaAdapter: RecyclerView.Adapter<CardCampanhaViewHolder>
    lateinit var eventoAdapter: RecyclerView.Adapter<CardEventoViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentOngCampanhasBinding
    private lateinit var viewModel: CampanhasEEventosViewModel
    private val listaEventos: MutableList<EventoDto> = mutableListOf()
    private val listaCampanhas: MutableList<CampanhaDto> = mutableListOf()
    private var filtroAtual: String = Variaveis().CAMPANHAS

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
        viewModel.minhasCampanhas.observe(viewLifecycleOwner, Observer {
            closeLoading()
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
        viewModel.meusEventos.observe(viewLifecycleOwner, Observer {
            closeLoading()
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
                carregaCampanhasUserLogado()
            } else if (filtroAtual.equals(Variaveis().EVENTOS)) {
                carregaEventosUserLogado()
            }

        })
    }

    private fun carregaCampanhasUserLogado() {
        startLoading()
        viewModel.getCampanhasUserLogado()
    }

    private fun carregaEventosUserLogado() {
        startLoading()
        viewModel.getEventosUserLogado()
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
                    carregaCampanhasUserLogado();
                } else if (filtroAtual.equals(Variaveis().EVENTOS)) {
//                    carregaEventosUserLogado(textoDeBusca)
                    carregaEventosUserLogado()
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

