package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.adapters.CardEventoFinalAdapter
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngEventosBinding
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.UserDto
import br.com.salve_uma_vida_front.interfaces.CardEventoFinalListener
import br.com.salve_uma_vida_front.viewmodels.EventosViewModel


class PerfilOngEventosFragment(val user: UserDto, val idOng: Int) : Fragment(), CardEventoFinalListener {
    lateinit var binding: FragmentPerfilOngEventosBinding
    private val listaEventos: MutableList<EventoDto> = mutableListOf()
    private lateinit var viewModel: EventosViewModel
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilOngEventosBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(EventosViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        configuraObservers()
        carregaEventos()
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.ongPerfilEventosCardsEventos
        mRecyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(requireContext())
        val eventoFinalAdapter = CardEventoFinalAdapter(
            listaEventos,
            this
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = eventoFinalAdapter
    }

    private fun configuraObservers() {
        viewModel.eventosDeUmUser.observe(viewLifecycleOwner, Observer {
            listaEventos.clear()
            if (it != null) {
                listaEventos.addAll(it)
            }
            mRecyclerView.adapter!!.notifyDataSetChanged()
        })
    }

    private fun carregaEventos() {
        viewModel.getEventosDeUmUserPeloId(idOng)
    }

    companion object {
        @JvmStatic
        fun newInstance(user: UserDto, idOng: Int) = PerfilOngEventosFragment(user,idOng)
    }

    override fun abreEvento(evento: EventoDto) {
        Log.d("teste","Cliquei pra abrir o evento")
    }

    override fun abrePerfilOng(id: Int) {
        Toast.makeText(requireContext(),"Você já está no perfil da ong", Toast.LENGTH_SHORT)
    }
}