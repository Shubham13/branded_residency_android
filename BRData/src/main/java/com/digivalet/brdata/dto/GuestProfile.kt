package com.digivalet.brdata.dto

import java.io.Serializable

data class GuestProfile(
    val `data`: List<GuestData>,
    val headerData: List<HeaderData>,
    val title: String
) : Serializable

data class GuestData(
    val guestId: String,
    val subTitles: List<GuestProfileSubTitle>,
    val title: String,
    val type: String
) : Serializable

data class HeaderData(
    val filterId: String,
    val image: String,
    val title: String,
    val type: String,
    var isSelected : Boolean = false
) : Serializable

data class GuestProfileSubTitle(
    val address: String,
    val count: String,
    val date: String,
    val email: String,
    val gender: String,
    val image: String,
    val phone: String,
    val subTitleId: String,
    val title: String
) : Serializable