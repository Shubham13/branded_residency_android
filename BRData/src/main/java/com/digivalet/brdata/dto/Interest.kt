package com.digivalet.brdata.dto

data class Interest(
    val `data`: List<InterestData>,
    val title: String
)

data class InterestData(
    val subTitles: List<InterestSubTitle>,
    val title: String
)

data class InterestSubTitle(
    val color: String,
    val subTitleId: String,
    val tintColor: String,
    val title: String
)