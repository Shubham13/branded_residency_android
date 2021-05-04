package com.paragon.sensonic.data

data class OtpRequest(val session: String = "",
                      val brandId: String = "",
                      val secretLoginCode: String = "",
                      val propertyId: String = "",
                      val email: String = "", val scope: String="")