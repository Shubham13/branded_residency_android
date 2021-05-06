package com.paragon.sensonic.auth.dto

data class OtpVerifySession(val code: Int = 0,
                            val data: OtpData)

data class OtpData(val ChallengeParameters: ChallengeParameters,
                val ChallengeName: String = "",
                val Session: String = "")


data class ChallengeParameters(val email: String = "")