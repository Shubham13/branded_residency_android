package com.paragon.sensonic.auth.dto

data class OtpVerify(val code: Int = 0, val data: VerifyData)

data class VerifyData(val credentials: Credentials,
                val user: User)

data class Credentials(val accessKeyId: String = "",
                       val secretAccessKey: String = "",
                       val expireTime: String = "",
                       val refreshTokenExpiry: String = "",
                       val identityId: String = "",
                       val sessionToken: String = "",
                       val refreshToken: String = "")


data class User(val createdAt: String = "",
                val gender: String = "",
                val phone: String = "",
                val dob: String = "",
                val name: Name,
                val Id: String = "",
                val type: String = "",
                val email: String = "",
                val username: String = "",
                val status: String = "",
                val resident: Resident)

data class Name(val fname: String = "",
                val lname: String = "")

data class Resident(val propertyUnit: PropertyUnit,
                    val bloodGroup: String = "",
                    val alternateContactInfo: AlternateContactInfo,
                    val residentType: String = "",
                    val Id: String = "")


data class PropertyUnit(val unitDetails: UnitDetails,
                        val unitNo: String = "",
                        val ownerOccupied: Boolean = false,
                        val Id: String = "",
                        val status: String = "",
                        val blockDetails: BlockDetails)

data class AlternateContactInfo(val phone: String = "",
                                val email: String = "")

data class UnitDetails(val floorNo: Int = 0,
                       val type: String = "")

data class BlockDetails(val blockName: String = "")