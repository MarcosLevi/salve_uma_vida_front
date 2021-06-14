package br.com.salve_uma_vida_front.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.FragmentMapaBinding
import br.com.salve_uma_vida_front.dto.ResponseMapDto
import br.com.salve_uma_vida_front.models.MapPinType
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.utils.LocationUtils
import br.com.salve_uma_vida_front.viewmodels.MapViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment : Fragment() {
    private lateinit var listaMapInfo: List<ResponseMapDto>
    private lateinit var endereco: LatLng
    private var now: Marker? = null
    private val PERMISSION_ID = 1000
    var map: GoogleMap? = null
    var navController: NavController? = null
    private lateinit var viewModelMap: MapViewModel
    private lateinit var binding: FragmentMapaBinding
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
        map!!.setOnInfoWindowClickListener { marker ->
            val mapInfo = marker.tag as ResponseMapDto
            abrePerfilMapInfo(mapInfo)
        }
        carregaMapInfo()
        locationUtils.startLocationUpdates()
    }

    private fun abrePerfilMapInfo(mapInfo: ResponseMapDto) {
        if (mapInfo.type.equals(MapPinType.NGO))
            navController!!.navigate(MapaFragmentDirections.actionMapaFragmentToPerfilOngFragment(mapInfo.id))
        else if(mapInfo.type.equals(MapPinType.EVENT))
            navController!!.navigate(MapaFragmentDirections.actionMapaFragmentToEventoDetalhadoFragment(mapInfo.id))

    }

    fun atualizaPosicaoAtual(){
        if(now != null){
            now!!.remove()
        }
        now = map!!.addMarker(MarkerOptions().position(endereco).title("Eu estou aqui"))
        if (isFixado)
            moveCamera()
    }
    fun addMarker(mapInfo: ResponseMapDto){
        var colorPin = BitmapDescriptorFactory.HUE_GREEN
        if (mapInfo.type.equals(MapPinType.NGO))
            colorPin=BitmapDescriptorFactory.HUE_VIOLET
        val endereco = LatLng(mapInfo.latitude.toDouble(), mapInfo.longitude.toDouble())
        val marker = map!!.addMarker(
            MarkerOptions().position(endereco).title(mapInfo.description).icon(
                BitmapDescriptorFactory
                    .defaultMarker(colorPin)
            )
        )
        marker.tag = mapInfo
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
        viewModelMap = ViewModelProviders.of(this).get(MapViewModel::class.java)
        locationUtils = LocationUtils(requireActivity())
        super.onCreate(savedInstanceState)
    }

    private fun configuraObservers() {
        viewModelMap.mapInfo.observe(viewLifecycleOwner, Observer {
            listaMapInfo = it
            for (mapInfo: ResponseMapDto in listaMapInfo){
                addMarker(mapInfo)
            }
        })
        locationUtils.myLocation.observe(viewLifecycleOwner, Observer {
            if (map != null) {
                endereco = LatLng(it.latitude, it.longitude)
                atualizaPosicaoAtual()
            }
        })
    }

    fun carregaMapInfo(){
        viewModelMap.getMapInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapaBinding.inflate(inflater, container, false)
        configuraToolbar()
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
        fixado()
        navController = Navigation.findNavController(view)
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

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
    }
}
