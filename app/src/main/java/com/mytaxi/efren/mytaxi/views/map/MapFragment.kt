package com.mytaxi.efren.mytaxi.views.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.*
import com.mytaxi.efren.mytaxi.R
import com.google.android.gms.maps.SupportMapFragment
import com.mytaxi.efren.mytaxi.model.HAMBURG
import com.mytaxi.efren.mytaxi.model.Vehicle
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker
import com.mytaxi.efren.mytaxi.utils.Status
import com.mytaxi.efren.mytaxi.views.listVehicles.VehicleAdapter
import kotlinx.android.synthetic.main.fragment_list_vehicles.*


class MapFragment : Fragment(), OnMapReadyCallback {

    @Inject
    lateinit var mapViewModelFactory: MapViewModelFactory
    private lateinit var viewModel: MapViewModel

    private var mapFragment: SupportMapFragment? = null
    private var mMap: GoogleMap? = null
    var selectedVehicleID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapFragment?.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_map, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, mapViewModelFactory).get(MapViewModel::class.java)

        viewModel.data.observe(this, Observer {
            when (it!!.status) {
                Status.LOADING -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT)
                Status.SUCCESS -> setMarkers(it.data!!)
                Status.ERROR -> Toast.makeText(context, it!!.message, Toast.LENGTH_SHORT)
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        return if (id == R.id.action_refresh) {
            viewModel.getVehicles(HAMBURG().area1 ,HAMBURG().area2)
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setMarkers(vehicles: List<Vehicle>) {
        mMap ?: return
        mMap!!.clear()
        for (vehicle in vehicles) {
            var marker = mMap!!.addMarker(MarkerOptions()
                    .position(LatLng(vehicle.coordinate.latitude, vehicle.coordinate.longitude))
                    .title(vehicle.fleetType)
                    .snippet("Heading: " + vehicle.heading)
                    .icon(defaultMarker(chooseIcon(vehicle.fleetType))))
            if(vehicle.id == selectedVehicleID)
                    marker.showInfoWindow()
        }
    }

    private fun chooseIcon(fleetType: String): Float {
        return when(fleetType){
            "TAXI" -> 48F
            "POOLING" -> 300F
            else -> 120F
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        mMap = googleMap
        with(mMap) {
            this!!.moveCamera(displayCityArea(HAMBURG().area1, HAMBURG().area2))
            setMarkers(viewModel.data.value!!.data!!)
        }
    }

    private fun displayCityArea(latLng1: LatLng, latLng2: LatLng): CameraUpdate? {
        val builder = LatLngBounds.Builder()

        builder.include(latLng1)
        builder.include(latLng2)

        return CameraUpdateFactory.newLatLngBounds(builder.build(), 100)
    }

    override fun onResume() {
        super.onResume()
        mapFragment?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapFragment?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapFragment?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapFragment?.onLowMemory()
    }

}