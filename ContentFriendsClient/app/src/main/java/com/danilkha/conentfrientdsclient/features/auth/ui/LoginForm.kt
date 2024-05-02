package com.danilkha.conentfrientdsclient.features.auth.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.danilkha.conentfrientdsclient.R

@Composable
fun LoginForm(
    loginState: LoginState,
    callback: LoginFormCallback,
){
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = loginState.username,
                onValueChange = callback::onUsernameChange,
                label = { Text(stringResource(R.string.username)) },
                isError = !loginState.isUsernameValid && loginState.showFieldError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = loginState.password,
                onValueChange = callback::onPasswordChange,
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError = !loginState.isPasswordValid && loginState.showFieldError,
                singleLine = true,
            )
            if (loginState.error != null) {
                Text(
                    text = loginState.error.label,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { callback.onLogin() }) {
                Text(stringResource(R.string.login))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.clickable { callback.onCreateAccount() },
                text = stringResource(R.string.create_account),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

interface LoginFormCallback{
    fun onUsernameChange(username:String)
    fun onPasswordChange(password:String)
    fun onLogin()
    fun onCreateAccount()
}