
package com.tikhonov.shopsfinder.ui.maps

import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appolica.interactiveinfowindow.InfoWindow
import com.appolica.interactiveinfowindow.InfoWindowManager
import com.appolica.interactiveinfowindow.customview.TouchInterceptFrameLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.tikhonov.shopsfinder.*
import com.tikhonov.shopsfinder.data.model.Poi
import com.tikhonov.shopsfinder.databinding.FragmentMapBinding
import com.tikhonov.shopsfinder.ui.favourites.FavouritesFragment
import com.tikhonov.shopsfinder.ui.infoWindow.InfoWindowFragment
import com.tikhonov.shopsfinder.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var lastKnownLocation: Location
    private lateinit var infoWindowManager: InfoWindowManager
    private lateinit var infoWindow: InfoWindow
    private lateinit var mapView: MapView
    private val viewModel by viewModels<MapsViewModel>()
    // viewBinding
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemFavourites -> navigateTo(FavouritesFragment(), addToBackStack = true)
            R.id.menuItemLogOut ->
                viewModel.logOut(requireContext()) { parentActivity.showAuthUI() }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_map_menu, menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(toolbar = binding.toolbar, showMenu = true)

        viewModel.nearbyPlaces.observe(viewLifecycleOwner, { poiList ->
            displayPoi(poiList)
        })

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        val mapViewContainer = view.findViewById<TouchInterceptFrameLayout>(R.id.mapViewContainer)
        mapView.getMapAsync(this)
        infoWindowManager = InfoWindowManager(childFragmentManager)
        infoWindowManager.onParentViewCreated(mapViewContainer, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    getLastKnownLocation()
                } else {
                    // STUB: Should be done in real app )
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }

        binding.fabRefreshData.setOnClickListener {
            showCurrentLocation()
        }
    }

    private fun displayPoi(poiList: List<Poi>) {
        for (poi in poiList) {
            val marker = mMap.addMarker(
                MarkerOptions()
                    .icon(
                        BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                    .position(LatLng(poi.lat, poi.long))
                    .title(poi.name)
            )
            marker.tag = poi
        }
    }

    private fun showCurrentLocation() {
        if (this::mMap.isInitialized && this::lastKnownLocation.isInitialized) {

            val currentLocation = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
            viewModel.getNearByPlaces(
                latitude = currentLocation.latitude,
                longitude = currentLocation.longitude,
                radius = SHOPS_SEARCHING_RADIUS_IN_METERS,
                type = TYPE_GOOGLE_PLACES_STORE
            )

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, GOOGLE_MAPS_CAMERA_ZOOM))
            mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    activity, R.raw.map_style
                )
            )

            mMap.addMarker(
                MarkerOptions()
                    .icon(
                        BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                    .position(currentLocation)
            )
            mMap.setOnMarkerClickListener(this)

        }
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lastKnownLocation = location!!
                showCurrentLocation()
            }
    }

    private fun checkPermission(): Boolean {

        return if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PERMISSION_GRANTED) {
            getLastKnownLocation()
            true
        } else
        {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // STUB: Should be done in real app )
                return false
            }
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            false
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        checkPermission()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        infoWindowManager.onMapReady(googleMap)
        showCurrentLocation()
    }

    override fun onMarkerClick(marker: Marker?): Boolean {

        runCatching {
            val currentPoi = (marker?.tag as Poi)
            val fragment = InfoWindowFragment.newInstance(
                poi = currentPoi
            )
        infoWindow = InfoWindow(marker, InfoWindow.MarkerSpecification(0, INFO_WINDOW_VERTICAL_OFFSET), fragment)
            infoWindowManager.toggle(infoWindow, true)
        }
        return true
    }
}
