package com.digivalet.brdata.dto

import java.io.Serializable

data class Pets(
    val `data`: List<PetsData>,
    val title: String
) : Serializable

data class PetsData(
    val image: String,
    val name: String,
    val residentId: String,
    val type: String
) : Serializable