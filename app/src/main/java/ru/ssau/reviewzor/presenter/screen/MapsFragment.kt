package ru.ssau.reviewzor.presenter.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import ru.ssau.reviewzor.databinding.FragmentMapsBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment

class MapsFragment : BaseFragment<FragmentMapsBinding>(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    override fun initBinding(inflater: LayoutInflater): FragmentMapsBinding =
        FragmentMapsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStartMap()
        setupLocationClient()
    }

    private fun setupOnPlaceClick() {
        map.setOnPoiClickListener {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_LONG).show()
        }
    }

    private fun setStartMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(binding.mapFragment.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        setupOnPlaceClick()
        requireActivity().lifecycleScope.launchWhenCreated {
            getCurrentLocation()
        }
    }

    private suspend fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissions()
        } else {
            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnCompleteListener {
                val location = it.result
                if (location != null) {
                    val locationLongLat = LatLng(location.latitude, location.latitude)
                    val update = CameraUpdateFactory.newLatLngZoom(locationLongLat, 16.0F)
                    map.moveCamera(update)
                } else {
                    Log.d(TAG, "No Location Found!!")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requireActivity().lifecycleScope.launchWhenCreated {
                    getCurrentLocation()
                }
            } else {
                Log.e(TAG, "Location permission denied")
            }
        }
    }

    companion object {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsFragment"
    }
}