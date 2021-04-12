package com.digivalet.brdata.dto

import java.io.Serializable

data class Friends(
    val `data`: List<FriendsData>,
    val title: String
) : Serializable

data class FriendsData(
    val image: String,
    val name: String,
    val residentId: String,
    val type: String
): Serializable


