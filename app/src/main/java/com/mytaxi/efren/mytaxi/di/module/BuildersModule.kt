package com.mytaxi.efren.mytaxi.di.module

import com.mytaxi.efren.mytaxi.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeReviewsActivity(): MainActivity
}