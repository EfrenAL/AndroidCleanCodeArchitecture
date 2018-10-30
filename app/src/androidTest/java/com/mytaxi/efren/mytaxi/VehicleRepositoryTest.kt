package com.mytaxi.efren.mytaxi

import android.support.test.runner.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import com.mytaxi.efren.mytaxi.model.Coordinates
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.repository.VehicleRepository
import com.mytaxi.efren.mytaxi.services.MyTaxiApi
import com.mytaxi.efren.mytaxi.services.VehiclesResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import java.util.concurrent.Executor

@RunWith(AndroidJUnit4::class)
class VehicleRepositoryTest {

    @Mock
    lateinit var myTaxiApi: MyTaxiApi

    @InjectMocks
    lateinit var vehicleRepository: VehicleRepository

    companion object {
        @BeforeClass
        fun setUpRxSchedulers() {
            val immediate = object : Scheduler() {

                override fun scheduleDirect(run: Runnable, delay: Long, unit: java.util.concurrent.TimeUnit): Disposable {
                    // this prevents StackOverflowErrors when scheduling with a delay
                    return super.scheduleDirect(run, 0, unit)
                }
                override fun createWorker(): Scheduler.Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }

            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
        }
    }



    @Test
    fun getVehiclesTestSuccess() {
        Mockito.`when`(myTaxiApi.getVehicles(1.123, 1.123, 2.123, 3.123)).thenReturn(mockSuccessData())
        vehicleRepository.getVehicles(LatLng(1.123, 1.123), LatLng(2.123, 3.123))
        Mockito.verify(vehicleRepository.data.value.orEmpty() != null)
        Mockito.verify(vehicleRepository.data.value.orEmpty().size == 20)
    }

    private fun mockSuccessData(): Observable<Response<VehiclesResponse>>? {
        var vehicle1 = Vehicle(439670, Coordinates(53.46036882190762, 9.909716434648558), "POOLING", 344.19529122029735)
        var vehicle2 = Vehicle(739330, Coordinates(53.668806556867445, 10.019908942943804), "TAXI", 245.2005654202569)
        var vehicle3 = Vehicle(145228, Coordinates(53.58500747958201, 9.807045083858156), "POOLING", 71.63840043828377)
        var vehicleList: List<Vehicle> = listOf(vehicle1, vehicle2, vehicle3)
        return Observable.create<Response<VehiclesResponse>> { Response.success(VehiclesResponse(vehicleList)) }
    }
}
