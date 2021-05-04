package com.paragon.sensonic.auth

data class EmailOtpVerify(
    val session: String = "",
    val brandId: String = "",
    val secretLoginCode: String = "",
    val propertyId: String = "",
    val email: String = "", val scope: String = ""
)


data class MobileOtpVerify(
    val session: String = "",
    val brandId: String = "",
    val secretLoginCode: String = "",
    val propertyId: String = "",
    val phone: String = "", val scope: String = ""
)