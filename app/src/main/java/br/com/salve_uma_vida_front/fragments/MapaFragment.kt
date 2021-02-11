package br.com.salve_uma_vida_front.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.CardCampanhaFinalAdapter
import br.com.salve_uma_vida_front.adapters.CardEventoFinalAdapter
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.databinding.FragmentBothMapaBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.hideKeyboard
import br.com.salve_uma_vida_front.utils.LocationUtils
import br.com.salve_uma_vida_front.viewmodels.CampanhasViewModel
import br.com.salve_uma_vida_front.viewmodels.EventosViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment : Fragment() {
    private lateinit var listaEventos: List<EventoDto>
    private lateinit var endereco: LatLng
    private var now: Marker? = null
    private val PERMISSION_ID = 1000
    var map: GoogleMap? = null
    private lateinit var viewModelCampanha: CampanhasViewModel
    private lateinit var viewModelEvento: EventosViewModel
    private lateinit var binding: FragmentBothMapaBinding
    private lateinit var locationUtils: LocationUtils
    private var isFixado = true

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map!!.uiSettings.isZoomControlsEnabled = true
        map!!.setOnCameraMoveStartedListener { reason: Int ->
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                naoFixado()
            } else if (reason == GoogleMap.OnCameraMoveStartedListener
                    .REASON_API_ANIMATION) {
                naoFixado()
            }
        }
        carregaEventos()
        locationUtils.startLocationUpdates()
    }

    fun atualizaPosicaoAtual(){
        if(now != null){
            now!!.remove()
        }
        now = map!!.addMarker(MarkerOptions().position(endereco).title("Eu estou aqui"))
        if (isFixado)
            moveCamera()
    }
    fun addMarkerEvento(evento: EventoDto){
        val endereco = LatLng(evento.latitude!!.toDouble(), evento.longitude!!.toDouble())
        map!!.addMarker(MarkerOptions().position(endereco).title(evento.titulo).icon(
            BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
    }

    fun moveCamera(){
        val zoomLevel = 15f
        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(endereco, zoomLevel))
        map!!.moveCamera(CameraUpdateFactory.newLatLng(endereco))
    }

    override fun onPause() {
        locationUtils.stopLocationUpdates()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelCampanha = ViewModelProviders.of(this).get(CampanhasViewModel::class.java)
        viewModelEvento = ViewModelProviders.of(this).get(EventosViewModel::class.java)
        locationUtils = LocationUtils(requireActivity())
        super.onCreate(savedInstanceState)
    }

    private fun configuraObservers() {
        viewModelEvento.eventos.observe(viewLifecycleOwner, Observer {
            listaEventos = it
            for (evento: EventoDto in listaEventos){
                addMarkerEvento(evento)
            }
        })
        locationUtils.myLocation.observe(viewLifecycleOwner, Observer {
            if (map != null) {
                endereco = LatLng(it.latitude, it.longitude)
                atualizaPosicaoAtual()
            }
        })
    }

    fun carregaEventos(){
        viewModelEvento.getEventos("")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBothMapaBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun fixado() {
        if (!isFixado){
            isFixado = true
            binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_gps_fixed_24
            ))
            moveCamera()
        }

    }

    private fun naoFixado() {
        if (isFixado){
            isFixado = false
            binding.floatingActionButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_gps_not_fixed_24
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==PERMISSION_ID){
            if (grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Log.d("Debug","Você tem a permissão")
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configuraObservers()
        binding.floatingActionButton.setOnClickListener {
            fixado()
        }
        configuraMapa(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }


    private fun configuraMapa(savedInstanceState: Bundle?) {
        val mapFragment = binding.mapa
        mapFragment.onCreate(savedInstanceState)
        mapFragment.onResume()
        mapFragment.getMapAsync(callback)
    }
}
