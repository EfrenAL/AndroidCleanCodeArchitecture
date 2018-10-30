package com.mytaxi.efren.mytaxi.views.listVehicles

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mytaxi.efren.mytaxi.R
import com.mytaxi.efren.mytaxi.model.Vehicle
import kotlinx.android.synthetic.main.item_vehicle.view.*
import java.util.Random

class VehicleAdapter(val vehicles: List<Vehicle>, val context: Context, val listener: OnItemClickListener) : RecyclerView.Adapter<VehicleViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Vehicle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): VehicleViewHolder {
        return VehicleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false))
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position], listener)
    }

}

class VehicleViewHolder (var view: View) : RecyclerView.ViewHolder(view) {

    private val fleetType = view.tv_fleetType
    private val heading = view.tv_heading
    private val rating = view.rb_rating

    fun bind(vehicle: Vehicle, listener: VehicleAdapter.OnItemClickListener) {
        val random = Random()
        fleetType.text = vehicle.fleetType
        rating.rating = random.nextInt(6).toFloat()
        heading.text = vehicle.heading.toString()
        view.setOnClickListener {
            listener.onItemClick(vehicle)
        }
    }

}
