package com.paragon.sensonic.auth.dto

data class PasswordLessLogin(val code: Int = 0,
                             val data: Data)

data class Data(val Session: String = "")