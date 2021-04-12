package com.digivalet.brdata.dto

data class GuestType(
    val `data`: List<TypeData>
)

data class TypeData(
    val title: String,
    val typeID: Int
)