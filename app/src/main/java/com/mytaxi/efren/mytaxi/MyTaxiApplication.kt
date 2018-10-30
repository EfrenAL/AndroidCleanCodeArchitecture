package com.mytaxi.efren.mytaxi

import android.app.Activity
import android.app.Application
import com.mytaxi.efren.mytaxi.di.module.AppModule
import com.mytaxi.efren.mytaxi.di.module.NetworkModule
import com.mytaxi.efren.mytaxi.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyTaxiApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .builder()
                .appModule(AppModule(this, baseContext))
                .networkModule(NetworkModule)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}