package com.mytaxi.efren.mytaxi.views.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.repository.VehicleRepository
import com.mytaxi.efren.mytaxi.utils.Resource
import javax.inject.Inject

class MapViewModel @Inject constructor(val vehicleRepository: VehicleRepository) : ViewModel() {

    val data: LiveData<Resource<List<Vehicle>>>

    init {
        data = vehicleRepository.data
    }

    fun getVehicles(latLng1: LatLng, latLng2: LatLng) {
        vehicleRepository.getVehicles(latLng1, latLng2)
    }
}