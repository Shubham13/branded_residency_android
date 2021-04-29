package com.paragon.brdata.dto

import java.io.Serializable

data class Vehicle(val `data`: List<VehicleData>, val title: String) : Serializable

data class VehicleData(
    val image: String,
    val name: String,
    val residentId: String,
    val type: String
) : Serializable
