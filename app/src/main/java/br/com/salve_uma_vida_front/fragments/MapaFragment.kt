package br.com.salve_uma_vida_front.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.salve_uma_vida_front.databinding.FragmentBothMapaBinding
import br.com.salve_uma_vida_front.utils.LocationUtils
import br.com.salve_uma_vida_front.viewmodels.CampanhasViewModel
import br.com.salve_uma_vida_front.viewmodels.EventosViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment : Fragment() {
    private var now: Marker? = null
    private val PERMISSION_ID = 1000
    var map: GoogleMap? = null
    private lateinit var viewModelCampanha: CampanhasViewModel
    private lateinit var viewModelEvento: EventosViewModel
    private lateinit var binding: FragmentBothMapaBinding
    private lateinit var locationUtils: LocationUtils

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
    }

    fun moveCamera(endereco: LatLng){
        val zoomLevel = 17f
        if(now != null){
            now!!.remove()
        }
        now = map!!.addMarker(MarkerOptions().position(endereco).title("Eu estou aqui"))
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBothMapaBinding.inflate(inflater, container, false)
        locationUtils.startLocationUpdates()
        return binding.root
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
        configuraMapa(savedInstanceState)
        locationUtils.myLocation.observe(viewLifecycleOwner, Observer {
            if (map != null) {
                val endereco = LatLng(it.latitude, it.longitude)
                moveCamera(endereco)
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }


    private fun configuraMapa(savedInstanceState: Bundle?) {
        val mapFragment = binding.mapa
        mapFragment.onCreate(savedInstanceState)
        mapFragment.onResume()
        mapFragment.getMapAsync(callback)
    }
}
