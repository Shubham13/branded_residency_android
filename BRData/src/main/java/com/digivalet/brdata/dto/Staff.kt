package com.digivalet.brdata.dto

import java.io.Serializable

data class Staff(val `data`: List<StaffData>, val title: String) : Serializable

data class StaffData(
    val image: String,
    val name: String,
    val residentId: String,
    val type: String
) : Serializable