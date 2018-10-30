package com.mytaxi.efren.mytaxi.services

import com.mytaxi.efren.mytaxi.model.Vehicle
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyTaxiApi {

    @GET("/")
    fun getVehicles(@Query("p1Lat") p1Lat: Double,
                    @Query("p1Lon") p1Lon: Double,
                    @Query("p2Lat") p2Lat: Double,
                    @Query("p2Lon") p2Lon: Double): Observable<Response<VehiclesResponse>>
}

const val BASE_URL: String = "https://fake-poi-api.mytaxi.com"


data class VehiclesResponse(var poiList: List<Vehicle>)

