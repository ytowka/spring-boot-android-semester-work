package com.danilkha.conentfrientdsclient.features.users.ui.edit

import com.danilkha.conentfrientdsclient.features.users.ui.UserModel
import com.danilkha.conentfrientdsclient.features.users.ui.UserRoleModel

data class EditUserState(
    val initalUser: UserModel? = null,
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val role: UserRoleModel = UserRoleModel.USER,
    val isBlocked: Boolean = false,
    val userSaved: Boolean = false,
    val isMe: Boolean = true,
){

    val isChanged by lazy {
        initalUser?.fullName != fullName
                || initalUser.email != email
                || initalUser.phone != phone
                || initalUser.role != role
                || initalUser.isBlocked != isBlocked
    }
}