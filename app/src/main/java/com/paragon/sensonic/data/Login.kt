package com.paragon.sensonic.data

data class Login(val scope: String = "",
                 val brandId: String = "",
                 val propertyId: String = "",
                 val email: String = "")


data class LoginMobile(val scope: String = "",
                 val brandId: String = "",
                 val propertyId: String = "",
                 val phone: String = "")