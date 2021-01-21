package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.CardCampanhaEditavelAdapter
import br.com.salve_uma_vida_front.adapters.CardCampanhaFinalAdapter
import br.com.salve_uma_vida_front.adapters.CardEventoEditavelAdapter
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngCampanhasBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.interfaces.CardCampanhaFinalListener
import br.com.salve_uma_vida_front.viewholders.CardCampanhaEditavelViewHolder
import br.com.salve_uma_vida_front.viewholders.CardCampanhaFinalViewHolder
import br.com.salve_uma_vida_front.viewmodels.CampanhasViewModel

class PerfilOngCampanhasFragment : Fragment(), CardCampanhaFinalListener {

    lateinit var binding: FragmentPerfilOngCampanhasBinding
    private val listaCampanhas: MutableList<CampanhaDto> = mutableListOf()
    private lateinit var viewModelCampanha: CampanhasViewModel
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilOngCampanhasBinding.inflate(inflater, container, false)
        viewModelCampanha = ViewModelProviders.of(this).get(CampanhasViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        configuraObservers()
        carregaCampanhas()
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.ongPerfilCampanhasCardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(requireContext())
        val campanhaFinalAdapter = CardCampanhaFinalAdapter(
            listaCampanhas,
            requireContext(),
            this
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = campanhaFinalAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance() = PerfilOngCampanhasFragment()
    }

    private fun carregaCampanhas() {
        viewModelCampanha.getCampanhasUserLogado()
    }

    private fun configuraObservers() {
        viewModelCampanha.minhasCampanhas.observe(viewLifecycleOwner, Observer {
            listaCampanhas.clear()
            if (it != null) {
                listaCampanhas.addAll(it)
            }
            mRecyclerView.adapter!!.notifyDataSetChanged()
        })
    }

    override fun abrePerfilOng(campanha: CampanhaDto) {
        Toast.makeText(requireContext(),"Você já está no perfil da ong",Toast.LENGTH_SHORT)
    }

    override fun abreCampanha(campanha: CampanhaDto) {
        Log.d("teste","Cliquei pra abrir a campanha")
    }
}