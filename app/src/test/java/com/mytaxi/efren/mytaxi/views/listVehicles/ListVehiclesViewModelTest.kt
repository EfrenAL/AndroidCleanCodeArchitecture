package com.mytaxi.efren.mytaxi.views.listVehicles

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.view.View
import com.google.android.gms.maps.model.LatLng
import com.mytaxi.efren.mytaxi.model.Coordinates
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.repository.RxImmediateSchedulerRule
import com.mytaxi.efren.mytaxi.repository.VehicleRepository
import com.mytaxi.efren.mytaxi.services.MyTaxiApi
import com.mytaxi.efren.mytaxi.services.VehiclesResponse
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner.StrictStubs::class)
class ListVehiclesViewModelTest{


    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    lateinit var viewModel: ListVehiclesViewModel


    @Before
    fun setUp() {
        viewModel = ListVehiclesViewModel(vehicleRepository)
    }


    @Test
    fun getVehiclesTestSuccess() {

        //Mockito.`when`(vehicleRepository.getVehicles(LatLng(1.123, 1.123), LatLng(2.123, 3.123))).thenReturn(mockSuccessData())
        var data = viewModel.getVehicles(LatLng(1.123, 1.123), LatLng(2.123, 3.123))

        //assert(data.value!!.size == 3)
    }

    private fun mockSuccessData(): LiveData<List<Vehicle>>? {
        var vehicle1 = Vehicle(439670, Coordinates(53.46036882190762, 9.909716434648558), "POOLING", 344.19529122029735)
        var vehicle2 = Vehicle(739330, Coordinates(53.668806556867445, 10.019908942943804), "TAXI", 245.2005654202569)
        var vehicle3 = Vehicle(145228, Coordinates(53.58500747958201, 9.807045083858156), "POOLING", 71.63840043828377)


        var data: MutableLiveData<List<Vehicle>> = MutableLiveData()
        data.value = listOf(vehicle1, vehicle2, vehicle3)

        return data
    }
}