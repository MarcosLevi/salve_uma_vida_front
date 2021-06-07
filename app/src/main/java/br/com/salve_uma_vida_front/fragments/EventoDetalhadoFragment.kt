package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import br.com.salve_uma_vida_front.*
import br.com.salve_uma_vida_front.databinding.FragmentEventoDetalhadoBinding
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.viewmodels.EventosViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

class EventoDetalhadoFragment : Fragment() {
    var navController: NavController? = null
    private lateinit var binding: FragmentEventoDetalhadoBinding
    private lateinit var evento: EventoDto
    private lateinit var viewModelEvento: EventosViewModel

    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        map= googleMap
        getEventoPeloId()
    }

    private fun setaEventoNoMapa() {
        val enderecoDoEvento = LatLng(evento.latitude!!.toDouble(), evento.longitude!!.toDouble())
        map.addMarker(MarkerOptions().position(enderecoDoEvento).title(evento.endereco))
        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(enderecoDoEvento, zoomLevel))
        map.moveCamera(CameraUpdateFactory.newLatLng(enderecoDoEvento))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEventoDetalhadoBinding.inflate(inflater, container, false)
        viewModelEvento = ViewModelProviders.of(this).get(EventosViewModel::class.java)
        configuraObservers()
        configuraToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        configuraMapa(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getEventoPeloId() {
        mostraLoading()
        viewModelEvento.getEventoId(getEventoByArgs())
    }

    fun mostraLoading(){
        binding.eventoDetalhadoCardLoading.visibility = View.VISIBLE
        binding.eventoDetalhadoTituloLoading.visibility = View.VISIBLE
        binding.eventoDetalhadoDataLoading.visibility = View.VISIBLE
        binding.eventoDetalhadoDescricaoLabelLoading.visibility = View.VISIBLE
        binding.eventoDetalhadoDescricaoLoading.visibility = View.VISIBLE
        binding.eventoDetalhadoEnderecoLabelLoading.visibility = View.VISIBLE
        binding.eventoDetalhadoEnderecoLoading.visibility = View.VISIBLE
    }

    fun escondeLoading(){
        binding.eventoDetalhadoCardLoading.visibility = View.GONE
        binding.eventoDetalhadoTituloLoading.visibility = View.GONE
        binding.eventoDetalhadoDataLoading.visibility = View.GONE
        binding.eventoDetalhadoDescricaoLabelLoading.visibility = View.GONE
        binding.eventoDetalhadoDescricaoLoading.visibility = View.GONE
        binding.eventoDetalhadoEnderecoLabelLoading.visibility = View.GONE
        binding.eventoDetalhadoEnderecoLoading.visibility = View.GONE
    }

    private fun configuraMapa(savedInstanceState: Bundle?) {
        val mapFragment = binding.eventoDetalhadoMap
        mapFragment.onCreate(savedInstanceState)
        mapFragment.onResume()
        mapFragment.getMapAsync(callback)
    }

    private fun setaCamposEvento() {
        Picasso.get()
            .load(evento.imagem)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.eventoDetalhadoImagem)
        binding.eventoDetalhadoTitulo.text = evento.titulo
        binding.eventoDetalhadoData.text = "Ocorrerá em ${FormatStringToDate(
            evento.data
        )}"
        binding.eventoDetalhadoDescricao.text = evento.descricao
        binding.eventoDetalhadoEndereco.text = evento.endereco
    }

    private fun configuraObservers() {
        viewModelEvento.eventoPeloId.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                evento = it
                setaCamposEvento()
                setaEventoNoMapa()
            }
            escondeLoading()
        })
    }

    private fun getEventoByArgs(): Int {
        val args: EventoDetalhadoFragmentArgs by navArgs()
        val idEvento = args.idEvento
        return idEvento
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.inflateMenu(R.menu.fragment_campanha_ou_evento_detalhado_menu)
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.campanhaOuEventoDetalhadoFragmentVaiParaPerfilOng -> {
                    navController!!.navigate(
                        EventoDetalhadoFragmentDirections.actionEventoDetalhadoFragmentToPerfilOngFragment(
                            evento.userId!!
                        )
                    )
                    true
                }
                else -> {
                    throw IllegalArgumentException("Item inexistente")
                }
            }
        }
    }
}