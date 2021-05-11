package com.paragon.sensonic.auth.dto

data class RefreshCredential(val code: Int = 0, val data: RefreshData)

data class RefreshData(val accessKeyId: String = "",
                val secretAccessKey: String = "",
                val expireTime: String = "",
                val refreshTokenExpiry: String = "",
                val identityId: String = "",
                val sessionToken: String = "",
                val idToken: String = "",
                val accessToken: String = "",
                val refreshToken: String = "")