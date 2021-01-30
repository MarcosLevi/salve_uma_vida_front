package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngInfoBinding
import br.com.salve_uma_vida_front.dto.UserDto
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PerfilOngInfoFragment(val user: UserDto) : Fragment() {

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
        val enderecoDoUsuario = LatLng(user.addressLatitude.toDouble(), user.addressLongitude.toDouble())
        map.addMarker(MarkerOptions().position(enderecoDoUsuario).title(user.address))
        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(enderecoDoUsuario, zoomLevel))
        map.moveCamera(CameraUpdateFactory.newLatLng(enderecoDoUsuario))
    }

    lateinit var binding: FragmentPerfilOngInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilOngInfoBinding.inflate(inflater, container, false)
        binding.ongPerfilInfoDescricao.text = user.detail
        binding.ongPerfilInfoEndereco.text = user.address
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
        fun newInstance(user: UserDto) = PerfilOngInfoFragment(user)
    }


}