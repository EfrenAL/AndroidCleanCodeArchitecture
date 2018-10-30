package com.mytaxi.efren.mytaxi.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.mytaxi.efren.mytaxi.model.Coordinates
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.services.MyTaxiApi
import com.mytaxi.efren.mytaxi.services.VehiclesResponse
import com.mytaxi.efren.mytaxi.utils.Resource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import org.junit.BeforeClass




@RunWith(MockitoJUnitRunner.StrictStubs::class)
class VehicleRepositoryTest {


    /*companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }*/

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    var myTaxiApi: MyTaxiApi  = mock(MyTaxiApi::class.java)
    var vehicleRepository = VehicleRepository(myTaxiApi)

    @Mock
    lateinit var observer: Observer<Resource<List<Vehicle>>>

    lateinit var testObserver: Observer<Resource<List<Vehicle>>>


    @Before
    fun setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        // override Schedulers.io()
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }



    }

    @Test
    fun getVehiclesTestSuccess() {

        `when`(myTaxiApi.getVehicles(1.123, 1.123, 2.123, 3.123)).thenReturn(mockSuccessData())

        val dataRequest = vehicleRepository.getVehicles(LatLng(1.123, 1.123), LatLng(2.123, 3.123))

        dataRequest.observeForever(observer)
        Mockito.verify(myTaxiApi, atLeastOnce()).getVehicles(1.123, 1.123, 2.123, 3.123)

        Mockito.verify(observer).onChanged(Resource.loading(null))
        Mockito.verify(observer).onChanged(mockResourceData())

    }

    private fun mockSuccessData(): Observable<Response<VehiclesResponse>>? {
        var vehicle1 = Vehicle(439670, Coordinates(53.46036882190762, 9.909716434648558), "POOLING", 344.19529122029735)
        var vehicle2 = Vehicle(739330, Coordinates(53.668806556867445, 10.019908942943804), "TAXI", 245.2005654202569)
        var vehicle3 = Vehicle(145228, Coordinates(53.58500747958201, 9.807045083858156), "POOLING", 71.63840043828377)
        var vehicleList: List<Vehicle> = listOf(vehicle1, vehicle2, vehicle3)
        return Observable.create<Response<VehiclesResponse>> { Response.success(VehiclesResponse(vehicleList)) }
    }

    private fun mockResourceData(): Resource<List<Vehicle>> {
        var vehicle1 = Vehicle(439670, Coordinates(53.46036882190762, 9.909716434648558), "POOLING", 344.19529122029735)
        var vehicle2 = Vehicle(739330, Coordinates(53.668806556867445, 10.019908942943804), "TAXI", 245.2005654202569)
        var vehicle3 = Vehicle(145228, Coordinates(53.58500747958201, 9.807045083858156), "POOLING", 71.63840043828377)
        var vehicleList: List<Vehicle> = listOf(vehicle1, vehicle2, vehicle3)
        return Resource.success(vehicleList)
    }
}
