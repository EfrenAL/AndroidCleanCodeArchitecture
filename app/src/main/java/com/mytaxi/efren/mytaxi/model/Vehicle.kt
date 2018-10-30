package com.mytaxi.efren.mytaxi.model


data class Vehicle(val id: Long, val coordinate: Coordinates, val fleetType: String, val heading: Double)

data class Coordinates(val latitude: Double, val longitude: Double)
