package com.paragon.brdata.dto

import com.paragon.utils.stickyheader.StickyMainData
import java.io.Serializable

data class GuestHistory(
    val `data`: List<HistoryData>,
    val title: String
)

data class HistoryData(
    val subTitles: List<GuestHistorySubTitle>,
    val title: String
)

data class GuestHistorySubTitle(
    val address: String,
    val time: String,
    val approve: String,
    val guestType : String,
    val date: String,
    val email: String,
    val gender: String,
    val image: String,
    val phone: String,
    val subTitleId: String,
    val title: String
) : Serializable,StickyMainData