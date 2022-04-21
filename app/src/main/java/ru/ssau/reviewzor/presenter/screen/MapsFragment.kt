package ru.ssau.reviewzor.presenter.screen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.R
import ru.ssau.reviewzor.databinding.FragmentMapsBinding
import ru.ssau.reviewzor.decodeUriStreamToSize
import ru.ssau.reviewzor.presenter.adapter.BookmarkInfoWindowAdapter
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.MapViewModel
import java.util.*

class MapsFragment : BaseFragment<FragmentMapsBinding>(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient

    private val mapsViewModel by viewModel<MapViewModel>()
    private val geocoder: Geocoder by lazy { Geocoder(requireContext(), Locale.getDefault()) }
    private var marker: Marker? = null

    override fun initBinding(inflater: LayoutInflater): FragmentMapsBinding =
        FragmentMapsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStartMap()
        setupLocationClient()
        setupPlacesClient()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMapReady(p0: GoogleMap) {
        map = p0
        setupOnPlaceClick()
        requireActivity().lifecycleScope.launchWhenCreated {
            getCurrentLocation()
        }
        map.setInfoWindowAdapter(BookmarkInfoWindowAdapter(requireActivity()))
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupOnPlaceClick() {
        map.setOnMapClickListener {
            val address = geocoder.getFromLocation(it.latitude, it.longitude, REQUEST_LOCATION)
            val addressString = "${address[0].thoroughfare} ${address[0].subThoroughfare}"
            val id = Base64.getEncoder().encodeToString(it.toString().toByteArray())
            displayMarker(id, "", it, addressString)
        }
        map.setOnPoiClickListener {
            val address = geocoder.getFromLocation(it.latLng.latitude, it.latLng.longitude, REQUEST_LOCATION)
            val addressString = "${address[0].thoroughfare} ${address[0].subThoroughfare}"
            displayMarker(it.placeId, it.name, it.latLng, addressString)
        }
    }

    private fun displayMarker(id: String, name: String, latLng: LatLng, address: String) {
        marker?.remove()
        marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(name)
        )
        val image = mapsViewModel.getImage(id)
        if (image == null || image.isEmpty()) {
            marker?.tag = R.drawable.no_product
        } else {
            val bitmap = getImageWithAuthority(image.toUri())
            marker?.tag = bitmap
        }

        map.setOnInfoWindowClickListener {
            handleInfoWindowClick(id, name, latLng, address)
            marker?.remove()
        }
    }

    private fun getImageWithAuthority(uri: Uri) =
        decodeUriStreamToSize(uri, 200, 100, requireContext())

    private fun handleInfoWindowClick(id: String, name: String, latLng: LatLng, address: String) {
        lifecycleScope.launchWhenCreated {
            mapsViewModel.addBookmarkFromPlace(id, name, latLng, address)
            findNavController().navigate(
                MapsFragmentDirections.actionMapsFragmentToPlaceDetailFragment(id)
            )
        }
    }

    companion object {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsFragment"
    }
}