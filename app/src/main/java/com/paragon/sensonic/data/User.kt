package com.paragon.sensonic.data

data class User(val cognitoSub: String = "",
                val phone: String = "",
                val fullName: String = "",
                val Id: String = "",
                val brandIds: List<String>?,
                val email: String = "",
                val username: String = "",
                val status: String = "")