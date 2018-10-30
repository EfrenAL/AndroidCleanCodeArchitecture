package com.mytaxi.efren.mytaxi.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem

import com.mytaxi.efren.mytaxi.R
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.views.listVehicles.ListVehiclesFragment
import com.mytaxi.efren.mytaxi.views.map.MapFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, ListVehiclesFragment.DisplayMapWithSelectedVehicle {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val listVehiclesFragment: ListVehiclesFragment = ListVehiclesFragment()
    private val mapFragment: MapFragment = MapFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_main, listVehiclesFragment, null)
                .commit()

        setBottomNavigation()

    }

    private fun setBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_list -> displayListVehiclesFragments()
                R.id.action_map -> displayMapFragments(null)
                else -> false
            }
        }
    }

    private fun displayMapFragments(vehicleId: Long?): Boolean {
        supportFragmentManager.popBackStackImmediate()
        mapFragment.selectedVehicleID = vehicleId

        if(listVehiclesFragment.isVisible)
            supportFragmentManager.beginTransaction()
                    .hide(listVehiclesFragment)
                    .commit()
        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_main, mapFragment, null)
                .commit()
        return true
    }

    private fun displayListVehiclesFragments(): Boolean {
        supportFragmentManager.popBackStackImmediate()
        if(mapFragment.isVisible)
            supportFragmentManager.beginTransaction()
                    .hide(mapFragment as Fragment)
                    .commit()
        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_main, listVehiclesFragment, null)
                .commit()
        return true
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun displayMap(vehicle: Vehicle) {

        bottom_navigation.menu.findItem(R.id.action_map).isChecked = true
        displayMapFragments(vehicle.id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
