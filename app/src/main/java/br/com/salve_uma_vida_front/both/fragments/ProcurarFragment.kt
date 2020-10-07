package br.com.salve_uma_vida_front.both.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.adapters.CardAdapter
import br.com.salve_uma_vida_front.both.viewholders.CardCampanhaViewHolder
import br.com.salve_uma_vida_front.both.viewmodels.ProcurarFragmentViewModel
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarBinding
import br.com.salve_uma_vida_front.repository.getListaTodosCards
import kotlin.math.log

class ProcurarFragment : Fragment(){
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<CardCampanhaViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothProcurarBinding
    private lateinit var viewModel: ProcurarFragmentViewModel

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
//        carregaEventos()
        setHasOptionsMenu(true)
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        mAdapter = CardAdapter(
            getListaTodosCards(),
            requireContext()
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun carregaEventos() {
        viewModel.getEvento(6)
        viewModel.listaEventos.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_both_procurar_menu, menu)
        var procurar:MenuItem = menu.findItem(R.id.bothProcurarFragmentPesquisar)
        var searchView = procurar.actionView as SearchView


        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.d("onQueryTextSubmit", p0!!)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("onQueryTextChange", p0!!)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bothProcurarFragmentFiltros->{
                viewModel.createDialog(fragmentManager)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}