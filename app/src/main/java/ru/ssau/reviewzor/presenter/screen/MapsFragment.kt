package ru.ssau.reviewzor.presenter.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import ru.ssau.reviewzor.R
import ru.ssau.reviewzor.databinding.FragmentMapsBinding
import ru.ssau.reviewzor.presenter.adapter.BookmarkInfoWindowAdapter
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.MapViewModel

class MapsFragment : BaseFragment<FragmentMapsBinding>(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient

    private val mapsViewModel by viewModel<MapViewModel>()

    override fun initBinding(inflater: LayoutInflater): FragmentMapsBinding =
        FragmentMapsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStartMap()
        setupLocationClient()
        setupPlacesClient()
    }

    private fun setupOnPlaceClick() {
        map.setOnPoiClickListener {
            Log.d(TAG, it.name + it.latLng + it.placeId)
            displayPoiDisplayStep(it)
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

    private fun setupPlacesClient() {
        Places.initialize(requireContext(), getString(R.string.key_api))
        placesClient = Places.createClient(requireContext())
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

    private fun getCurrentLocation() {
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
                    val locationLongLat = LatLng(location.latitude, location.longitude)
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

    private fun displayPoiDisplayStep(point: PointOfInterest) {
        map.setInfoWindowAdapter(BookmarkInfoWindowAdapter(requireActivity()))

        val marker = map.addMarker(
            MarkerOptions()
                .position(point.latLng)
                .title(point.name)
        )
        marker?.tag = R.drawable.bm

        map.setOnInfoWindowClickListener {
            handleInfoWindowClick(point)
            marker?.remove()
        }
    }

    private fun handleInfoWindowClick(point: PointOfInterest) {
        lifecycleScope.launchWhenCreated {
            mapsViewModel.addBookmarkFromPlace(point)
            findNavController().navigate(
                MapsFragmentDirections.actionMapsFragmentToPlaceDetailFragment(point.placeId)
            )
        }
    }

    companion object {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsFragment"
    }
}