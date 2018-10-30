package com.mytaxi.efren.mytaxi.di.module

import android.arch.lifecycle.ViewModelProvider
import com.mytaxi.efren.mytaxi.repository.VehicleRepository
import com.mytaxi.efren.mytaxi.services.BASE_URL
import com.mytaxi.efren.mytaxi.services.MyTaxiApi
import com.mytaxi.efren.mytaxi.views.listVehicles.ListVehiclesViewModelFactory
import com.mytaxi.efren.mytaxi.views.map.MapViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    /**
     * Provides MyTaxiApi service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideGygApi(retrofit: Retrofit): MyTaxiApi {
        return retrofit.create(MyTaxiApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    /**
     * Provides vehicles repository.
     * @return vehicle repository
     */
    @Provides
    @JvmStatic
    @Singleton
    internal fun provideVehicleRepository(myTaxiApi: MyTaxiApi): VehicleRepository {
        return VehicleRepository(myTaxiApi)
    }

    @Provides
    fun provideListVehiclesViewModelFactory(factory: ListVehiclesViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun provideMapViewModelFactory(factory: MapViewModelFactory): ViewModelProvider.Factory = factory


}