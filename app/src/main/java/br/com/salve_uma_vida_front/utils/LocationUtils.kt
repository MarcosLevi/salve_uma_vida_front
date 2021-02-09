package br.com.salve_uma_vida_front.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*

class LocationUtils(private val activity: FragmentActivity) {
    private val PERMISSION_ID = 1000
    private lateinit var locationRequest: LocationRequest
    private var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity.applicationContext)
    private val _myLocation = MutableLiveData<Location>()
    val myLocation: LiveData<Location> = _myLocation

    fun startLocationUpdates(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        if (checkPermission()){
            if (isLocationEnabled()){
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
            }else{
                Toast.makeText(activity.applicationContext, "Liga o gps", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            requestPermission()
        }
    }
    fun stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback(){

        override fun onLocationResult(p0: LocationResult) {
            val lastLocation = p0.lastLocation
            _myLocation.value = lastLocation
        }
    }

    fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
            return true

        return false
    }
    fun requestPermission(){
        ActivityCompat.requestPermissions(activity, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID)
    }
    fun isLocationEnabled(): Boolean{
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
}