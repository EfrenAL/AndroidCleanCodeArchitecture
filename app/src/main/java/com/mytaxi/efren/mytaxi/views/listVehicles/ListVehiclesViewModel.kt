package com.mytaxi.efren.mytaxi.views.listVehicles

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mytaxi.efren.mytaxi.model.HAMBURG
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.repository.VehicleRepository
import com.mytaxi.efren.mytaxi.utils.Resource
import javax.inject.Inject

class ListVehiclesViewModel @Inject constructor(val vehicleRepository: VehicleRepository) : ViewModel() {

    val data: LiveData<Resource<List<Vehicle>>>

    init {
        data = vehicleRepository.getVehicles(HAMBURG().area1, HAMBURG().area2)
    }

    fun getVehicles(latLng1: LatLng, latLng2: LatLng): LiveData<Resource<List<Vehicle>>> {
        return vehicleRepository.getVehicles(latLng1, latLng2)
    }
}