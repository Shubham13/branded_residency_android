package com.paragon.sensonic.auth

data class EmailLoginMapper(val scope: String = "",
                                    val brandId: String = "",
                                    val propertyId: String = "",
                                    val email: String = "")

data class MobileLoginMapper(val scope: String = "",
                            val brandId: String = "",
                            val propertyId: String = "",
                            val phone: String = "")