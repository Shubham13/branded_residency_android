package com.paragon.sensonic.data

data class OtpRequest(val session: String = "",
                      val brandId: String = "",
                      val secretLoginCode: String = "",
                      val propertyId: String = "",
                      val email: String = "", val scope: String="")


data class OtpMobileRequest(val session: String = "",
                      val brandId: String = "",
                      val secretLoginCode: String = "",
                      val propertyId: String = "",
                      val phone: String = "", val scope: String="")