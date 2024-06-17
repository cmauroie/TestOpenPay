package com.fit.map.presentation

import android.Manifest
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.fit.core.permission.PermissionManager
import com.fit.map.R
import com.fit.map.databinding.FragmentMapsBinding
import com.fit.map.utils.isLocationEnabled
import com.fit.map.utils.showGpsDisabledDialog
import com.fit.map.utils.showPermissionDeniedDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment(), LocationService.LocationCallback {

    @Inject
    lateinit var permissionManager: PermissionManager

    private val viewModel: MapsViewModel by viewModels()

    private var googleMap: GoogleMap? = null


    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        val sydney = LatLng(4.7110, 74.0721)
        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Map"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private lateinit var binding: FragmentMapsBinding

    private var foregroundOnlyLocationServiceBound = false
    private var locationService: LocationService? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocationBinder
            locationService = binder.getService()
            foregroundOnlyLocationServiceBound = true
            locationService?.registerCallback(this@MapsFragment)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            foregroundOnlyLocationServiceBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        if (isServiceRunning(LocationService::class.java)) {
            bindLocationService()
        }
    }

    override fun onResume() {
        super.onResume()
        locationService?.setAppInForeground(true)
    }

    override fun onStop() {
        super.onStop()
        locationService?.setAppInForeground(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationService?.registerCallback(null)
    }

    private fun bindLocationService() {
        val context = requireContext()
        val intent = Intent(context, LocationService::class.java)

        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        if (mapFragment == null) {
            val supportMapFragment = SupportMapFragment.newInstance()
            childFragmentManager.beginTransaction()
                .replace(R.id.map, supportMapFragment)
                .commit()
            supportMapFragment.getMapAsync(callback)
        } else {
            mapFragment.getMapAsync(callback)
        }
        onClickButtons()

        requestNotificationPermission()
        observerViewModel()
    }

    private fun onClickButtons() {
        binding.btnStart.setOnClickListener {
            if (!isLocationEnabled(requireContext())) {
                showGpsDisabledDialog(requireContext())
            } else {
                requestLocationPermissions()
            }
        }
        binding.btnStop.setOnClickListener {
            stopService()
        }
    }

    private fun observerViewModel() {
        viewModel.uiModel.observe(this.requireActivity(), Observer {
            when (it) {
                MapsViewModel.UIModel.Loading -> {

                }

                MapsViewModel.UIModel.NoConnection -> {

                }

                is MapsViewModel.UIModel.ShowView -> {
                    it.locationModel.apply {
                        showMarker(latitude, longitude, date)
                    }
                }
            }
        })
    }

    private fun showMarker(latitude: Double, longitude: Double, date: String) {
        val markerPosition = LatLng(latitude, longitude)
        val marker = googleMap?.addMarker(
            MarkerOptions().position(markerPosition).title(date)
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(markerPosition))
        val zoomLevel = 15f
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, zoomLevel))
        marker?.showInfoWindow()
    }

    private fun requestLocationPermissions() {
        permissionManager.requestPermissions(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ), REQUEST_CODE_LOCATION, object : PermissionManager.PermissionCallback {
            override fun onPermissionGranted() {
                startService()
            }

            override fun onPermissionDenied() {
                stopService()
                showPermissionDeniedDialog(requireContext())
            }
        })
    }

    private fun requestNotificationPermission() {
        permissionManager?.requestPermissions(
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_CODE_POST_NOTIFICATIONS,
            object : PermissionManager.PermissionCallback {
                override fun onPermissionGranted() {
                }

                override fun onPermissionDenied() {
                    Toast.makeText(
                        requireContext(),
                        "Permiso necesario para mostrar las notificaciones de la app",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    private fun startService() {
        val intent = Intent(
            requireActivity().applicationContext,
            LocationService::class.java
        ).apply {
            action = LocationService.ACTION_START
        }
        requireActivity().applicationContext.startService(intent)
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        val intent = Intent(
            requireActivity().applicationContext,
            LocationService::class.java
        ).apply {
            action = LocationService.ACTION_STOP
        }
        requireActivity().applicationContext.startService(intent)
        if (foregroundOnlyLocationServiceBound) {
            foregroundOnlyLocationServiceBound = false
            requireActivity().unbindService(connection)
        }
    }

    override fun onLocationUpdate(latitude: Double, longitude: Double) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.sendLocationFirestore(
                latitude, longitude
            )
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager =
            requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    companion object {
        val REQUEST_CODE_POST_NOTIFICATIONS = 1001
        val REQUEST_CODE_LOCATION = 1002
    }
}