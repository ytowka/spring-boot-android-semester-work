package com.danilkha.conentfrientdsclient.features.users.ui.info

import androidx.compose.runtime.Immutable
import com.danilkha.conentfrientdsclient.features.users.ui.UserModel

@Immutable
data class UserInfoState(
    val userModel: UserModel? = null,
)