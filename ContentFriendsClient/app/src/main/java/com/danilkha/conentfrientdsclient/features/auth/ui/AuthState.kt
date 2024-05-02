package com.danilkha.conentfrientdsclient.features.auth.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.danilkha.conentfrientdsclient.R
import com.danilkha.contentfriends.validation.FieldValidation
import io.ktor.http.*

@Immutable
data class AuthState(
    val loginState: LoginState = LoginState(),
    val registerState: RegisterState = RegisterState(),
    val currentForm: Form = Form.LOGIN
)

enum class Form { LOGIN, REGISTER }

@Immutable
data class LoginState(
    val username: String = "",
    val password: String = "",
    val showFieldError: Boolean = false,
    val error: Error? = null,
){

    val isUsernameValid: Boolean by lazy {
            username.isNotBlank()
        }

    val isPasswordValid: Boolean by lazy {
        password.isNotBlank()
    }

    val isValid: Boolean by lazy {
        isUsernameValid && isPasswordValid
    }
}

@Immutable
data class RegisterState(
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val username: String = "",
    val imageUrl: Uri? = null,
    val passwordConfirmation: String = "",
    val fullName: String = "",
    val error: Error? = null,
    val showFieldError: Boolean = false,
){

    val isEmailValid: Boolean by lazy {
        FieldValidation.isEmailValid(email)
    }

    val isPasswordValid: Boolean by lazy {
        FieldValidation.isPasswordValid(password)
    }

    val isUsernameValid: Boolean by lazy {
        username.isNotBlank()
    }
    val isPasswordConfirmationValid: Boolean by lazy {
        passwordConfirmation == password
    }

    val isFullNameValid: Boolean by lazy {
        fullName.isNotBlank()
    }

    val isValid by lazy {
        isEmailValid && isPasswordConfirmationValid && isUsernameValid && isPasswordValid && isFullNameValid
    }
}

enum class Error(
    val resId: Int? = null
) {
    NETWORK(R.string.error_network),
    WRONG_CREDENTIALS(R.string.error_wrong_credentials),
    ALREADY_REGISTERED(R.string.error_already_registered),
    OTHER(R.string.error_other),
    FIELD(R.string.error_fields);

    val label: String
        @Composable
        get() = if(resId == null) "" else stringResource(id = resId)
}