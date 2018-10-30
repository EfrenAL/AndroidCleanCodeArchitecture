package com.mytaxi.efren.mytaxi.di.component

import com.mytaxi.efren.mytaxi.MyTaxiApplication
import com.mytaxi.efren.mytaxi.di.module.AppModule
import com.mytaxi.efren.mytaxi.di.module.BuildersModule
import com.mytaxi.efren.mytaxi.di.module.FragmentModule
import com.mytaxi.efren.mytaxi.di.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (BuildersModule::class), (AppModule::class), (NetworkModule::class), (FragmentModule::class)])
interface AppComponent {
    fun inject(app: MyTaxiApplication)
}