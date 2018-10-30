package com.mytaxi.efren.mytaxi.views.listVehicles

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class ListVehiclesViewModelFactory @Inject constructor(private val listVehiclesViewModel: ListVehiclesViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(listVehiclesViewModel::class.java!!)) {
            return listVehiclesViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
