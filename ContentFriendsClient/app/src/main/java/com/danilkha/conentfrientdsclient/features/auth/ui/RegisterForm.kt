package com.danilkha.conentfrientdsclient.features.auth.ui

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danilkha.conentfrientdsclient.R
import io.ktor.http.*


@Composable
fun RegisterForm(
    registerState: RegisterState,
    callback: RegisterFormCallback
){
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            ImagePicker(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp),
                selectedImage = registerState.imageUrl,
                onImagePicked = callback::onImagePicked,
            )
            OutlinedTextField(
                value = registerState.username,
                onValueChange = callback::onUsernameChange,
                label = { Text(stringResource(R.string.username)) },
                isError = !registerState.isUsernameValid && registerState.showFieldError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                maxLines = 1,
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = registerState.email,
                onValueChange = callback::onEmailChange,
                label = { Text(stringResource(R.string.email)) },
                isError = !registerState.isEmailValid && registerState.showFieldError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                maxLines = 1,
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = registerState.phone,
                onValueChange = callback::onPhoneChange,
                label = { Text(stringResource(R.string.phone_number)) },
                isError = !registerState.isEmailValid && registerState.showFieldError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                maxLines = 1,
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = registerState.fullName,
                onValueChange = callback::onFullNameChange,
                label = { Text(stringResource(R.string.full_name)) },
                isError = !registerState.isFullNameValid && registerState.showFieldError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                maxLines = 1,
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = registerState.password,
                onValueChange = callback::onPasswordChange,
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                isError = !registerState.isPasswordValid && registerState.showFieldError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                maxLines = 1,
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = registerState.passwordConfirmation,
                onValueChange = callback::onPasswordConfirmationChange,
                label = { Text(stringResource(R.string.confirm_password)) },
                visualTransformation = PasswordVisualTransformation(),
                isError = !registerState.isPasswordConfirmationValid && registerState.showFieldError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                maxLines = 1,
                singleLine = true,
            )

            if (registerState.error != null) {
                Text(
                    text = registerState.error.label,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { callback.onRegister() }) {
                Text(stringResource(R.string.register))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.clickable { callback.returnToSignIn() },
                text = stringResource(R.string.sign_in_to_account),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    selectedImage: Uri?,
    onImagePicked: (Uri?) -> Unit,
){
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if (uri != null) {
            onImagePicked(uri)
        }
    }

    val placeholder =  rememberVectorPainter(Icons.Filled.AccountCircle)
    AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                if(selectedImage != null){
                    onImagePicked(null)
                }else{
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                }
            }
            .size(56.dp),
        model = selectedImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        fallback = placeholder,
        error = placeholder
    )
}

interface RegisterFormCallback{
    fun onRegister()
    fun returnToSignIn()
    fun onUsernameChange(username:String)
    fun onPasswordChange(password:String)
    fun onFullNameChange(fullName:String)
    fun onEmailChange(email:String)
    fun onPhoneChange(phone:String)
    fun onImagePicked(uri: Uri?)
    fun onPasswordConfirmationChange(passwordConfirmation:String)
}