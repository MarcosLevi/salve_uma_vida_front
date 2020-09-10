package br.com.salve_uma_vida_front.ongs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.repository.getListaTodosCards
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.adapters.CardAdapter
import br.com.salve_uma_vida_front.databinding.FragmentOngCampanhasBinding

class CampanhasFragment : Fragment(), View.OnClickListener {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<CardAdapter.CardCampanhaViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentOngCampanhasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOngCampanhasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        configuraRecyclerView()
        val campoDePesquisa = binding.campanhasSearchView
        campoDePesquisa.queryHint = "Procurar por Nome, Descrição ou Itens"
        campoDePesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mAdapter as CardAdapter).filter.filter(newText)
                return false
            }

        })
        val ongCampanhasFragmentFab = binding.ongCampanhasFragmentFab
        ongCampanhasFragmentFab.setOnClickListener(this)
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

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ongCampanhasFragmentFab -> navController!!.navigate(R.id.action_ongCampanhasFragment_to_cadastroFragment)
        }
    }
}