package com.danilkha.contentfriends.validation

import java.util.regex.Pattern

object FieldValidation {


    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isEmailValid(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 8
    }
}