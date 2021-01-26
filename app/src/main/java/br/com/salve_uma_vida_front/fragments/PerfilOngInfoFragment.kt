package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngGaleriaBinding
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngInfoBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PerfilOngInfoFragment : Fragment() {

    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        map= googleMap
        /**
        To change the color of the marker

        To the map’s addmarker’s method add following line:
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        To add change the marker, add an image, marker.jpg to drawable folder and :
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))

        To zoom my map:
        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ajmer, zoomLevel))

        For your information, these are the zoom levels:
        1: World, 5: Landmass/continent, 10: City, 15: Streets and 20: Buildings
         */
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    lateinit var binding: FragmentPerfilOngInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilOngInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = binding.ongPerfilInfoMap
        mapFragment.onCreate(savedInstanceState)
        mapFragment.onResume()
        mapFragment.getMapAsync(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PerfilOngInfoFragment()
    }


}