package com.paragon.sensonic.data

data class Credentials(val accessKeyId: String = "",
                       val secretAccessKey: String = "",
                       val expireTime: String = "",
                       val refreshTokenExpiry: String = "",
                       val identityId: String = "",
                       val sessionToken: String = "",
                       val idToken: String = "",
                       val accessToken: String = "",
                       val refreshToken: String = "")