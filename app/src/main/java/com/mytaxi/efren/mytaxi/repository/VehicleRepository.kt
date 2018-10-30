package com.mytaxi.efren.mytaxi.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.google.android.gms.maps.model.LatLng
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.services.MyTaxiApi
import com.mytaxi.efren.mytaxi.services.VehiclesResponse
import com.mytaxi.efren.mytaxi.utils.Resource
import com.mytaxi.efren.mytaxi.utils.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(private val myTaxiApi: MyTaxiApi) {

    lateinit var subscription: Disposable

    //var data: MutableLiveData<List<Vehicle>> = MutableLiveData()
    //val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    //val errorMessage: MutableLiveData<String> = MutableLiveData()
    //val success: MutableLiveData<Boolean> = MutableLiveData()

    var data: MutableLiveData<Resource<List<Vehicle>>> = MutableLiveData()


    fun getVehicles(latLong1: LatLng, latLong2: LatLng): LiveData<Resource<List<Vehicle>>> {



        subscription = myTaxiApi.getVehicles(latLong1.latitude,  latLong1.longitude, latLong2.latitude, latLong2.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                        { onRetrieveSuccess(it) },
                        { onRetrieveError(it) }
                )

        return data
    }

    private fun onRetrieveError(it: Throwable?) {
        data.value = Resource.error(it?.message.toString(), null)
    }

    private fun onRetrieveSuccess(listOfVehicle: Response<VehiclesResponse>?) {
        data.value = Resource.success(listOfVehicle!!.body()!!.poiList)
    }

    private fun onRetrieveFinish() {
        data.value = Resource.loading(null)
    }

    private fun onRetrieveStart() {
        data.value = Resource.loading(null)
    }
}