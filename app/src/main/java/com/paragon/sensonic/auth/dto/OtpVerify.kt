package com.paragon.sensonic.data

data class OtpVerify(
    val code: Int = 0,
    val data: Data
)

data class Data(
    val credentials: Credentials,
    val user: User
)

data class User(
    val cognitoSub: String = "",
    val phone: String = "",
    val fullName: String = "",
    val Id: String = "",
    val brandIds: List<String>?,
    val email: String = "",
    val username: String = "",
    val status: String = ""
)

data class Credentials(
    val accessKeyId: String = "",
    val secretAccessKey: String = "",
    val expireTime: String = "",
    val refreshTokenExpiry: String = "",
    val identityId: String = "",
    val sessionToken: String = "",
    val idToken: String = "",
    val accessToken: String = "",
    val refreshToken: String = ""
)