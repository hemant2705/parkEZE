package com.edgetechs.parkeze.frags

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import com.edgetechs.parkeze.Nearby
import com.edgetechs.parkeze.R
import com.edgetechs.parkeze.ownerconfirm
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class booking : Fragment(),OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    lateinit var cnslayout: ConstraintLayout
    lateinit var currentBtn: TextView

    lateinit var pick: Button

    var lat: Double = 0.0
    var clat: Double = 0.0
    var clong: Double = 0.0
    var PERMISSION_ID = 1078

    var lng: Double = 0.0
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest


    fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity as Context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity as Context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    fun requestPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_ID
            )
        }

    }

   private fun isNetworkEnabled(): Boolean {
        val locationManager: LocationManager =activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
   }
    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You have the Access")
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        pick=view.findViewById(R.id.btnpark)
        //currentBtn = view.findViewById(com.edgetechs.parkeze.R.id.enter)
       //cnslayout = view.findViewById(com.edgetechs.parkeze.R.id.clsplash)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as Context)
        getLastLocation()
        //currentBtn.setOnClickListener {
          //  getLastLocation()
        //cnslayout.visibility=View.GONE
        //}
        pick.visibility=View.INVISIBLE
        pick.setOnClickListener {
            val intent =Intent(activity, Nearby::class.java)
            intent.putExtra("latitude", lat)
            intent.putExtra("longitude", lng)
            startActivity(intent)

        }
        return view;
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isNetworkEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    val location: Location = it.result

                    if (location == null) {
                        getNewLocation()
                    } else {
                        clat = location.latitude
                        clong = location.longitude
                        var set = LatLng(clat, clong)
                        mMap.isMyLocationEnabled = true
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(set, 15.0F))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(set, 15.0F))


                    }
                }

            } else {
                Toast.makeText(activity as Context, "Please Enable Location", Toast.LENGTH_SHORT).show()
            }
        } else (requestPermission())

    }


    private fun getNewLocation() {

        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 1000
        locationRequest.numUpdates = 2
        if (checkPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            var lastlocation = p0.lastLocation
            clat = lastlocation.latitude
            clong = lastlocation.longitude
            var set = LatLng(clat, clong)
            if(checkPermission()) { if (isNetworkEnabled()){mMap.isMyLocationEnabled = true}}
            mMap.addMarker(MarkerOptions().position(set).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE
            )))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(set, 15.0F))
            mMap.addCircle(CircleOptions().center(set).radius(4000.0).fillColor(Color.BLUE).strokeColor(Color.BLUE)
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLastLocation()
        // Add a marker in Sydney and move the camera
        if (checkPermission()){
            if (isNetworkEnabled()){
                mMap.isMyLocationEnabled = true
            }}
        mMap.setOnMapClickListener {
            var bir = it
            mMap.clear()
            mMap.addMarker(
                MarkerOptions().position(it).draggable(true).title("Your Shop Location")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            lat = it.latitude
            lng = it.longitude
            Toast.makeText(activity as Context, "$lat,$lng", Toast.LENGTH_SHORT).show()
            pick.visibility=View.VISIBLE

            //     mMap.setOnMarkerClickListener(this)

            mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDragStart(marker: Marker) {}
                override fun onMarkerDragEnd(marker: Marker) {
                    lat = marker.position.latitude
                    lng = marker.position.longitude
                    Toast.makeText(activity as Context, "$lat,$lng", Toast.LENGTH_SHORT).show()
                    pick.visibility=View.VISIBLE
                }

                override fun onMarkerDrag(marker: Marker) {}
            })
        }
    }



}




