package com.mytaxi.efren.mytaxi.di.module


import com.mytaxi.efren.mytaxi.views.listVehicles.ListVehiclesFragment
import com.mytaxi.efren.mytaxi.views.map.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeListVehiclesFragment(): ListVehiclesFragment

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment


}