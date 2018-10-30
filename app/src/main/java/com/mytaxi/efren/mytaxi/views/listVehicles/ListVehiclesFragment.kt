package com.mytaxi.efren.mytaxi.views.listVehicles

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mytaxi.efren.mytaxi.R
import com.mytaxi.efren.mytaxi.model.HAMBURG
import com.mytaxi.efren.mytaxi.model.Vehicle
import com.mytaxi.efren.mytaxi.utils.Status
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_list_vehicles.*
import javax.inject.Inject


class ListVehiclesFragment : Fragment() {

    @Inject
    lateinit var listVehiclesViewModelFactory: ListVehiclesViewModelFactory
    private lateinit var viewModel: ListVehiclesViewModel

    interface DisplayMapWithSelectedVehicle {
        fun displayMap(vehicle: Vehicle)
    }

    private var callback: DisplayMapWithSelectedVehicle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_vehicles, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        setupViewModel()
        //Setup recycleview
        rv_vehicle_list.layoutManager = LinearLayoutManager(activity!!.baseContext, LinearLayoutManager.VERTICAL, false)

        srl_vehicles_refresh.setOnRefreshListener { viewModel.getVehicles(HAMBURG().area1, HAMBURG().area2) }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as DisplayMapWithSelectedVehicle
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement DisplayMapWithSelectedVehicle")
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, listVehiclesViewModelFactory).get(ListVehiclesViewModel::class.java)
        viewModel.data.observe(this, Observer {
            when (it!!.status) {
                Status.LOADING -> srl_vehicles_refresh.isRefreshing = true
                Status.SUCCESS -> {
                    srl_vehicles_refresh.isRefreshing = false
                    rv_vehicle_list.adapter = VehicleAdapter(it.data!!, context!!, clickHandler())
                }
                Status.ERROR -> Toast.makeText(context, it!!.message, Toast.LENGTH_SHORT)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        return if (id == R.id.action_refresh) {
            viewModel.getVehicles(HAMBURG().area1, HAMBURG().area2)
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun clickHandler(): VehicleAdapter.OnItemClickListener {
        return object : VehicleAdapter.OnItemClickListener {
            override fun onItemClick(vehicle: Vehicle) {
                //Show fragment with marker selected
                callback?.displayMap(vehicle)
            }
        }
    }

}