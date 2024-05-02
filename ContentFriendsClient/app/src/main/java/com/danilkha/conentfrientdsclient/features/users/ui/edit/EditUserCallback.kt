package com.danilkha.conentfrientdsclient.features.users.ui.edit

import com.danilkha.conentfrientdsclient.features.users.ui.UserRoleModel

interface EditUserCallback {

    fun changeFullName(fullName: String)
    fun changeEmail(email: String)
    fun changePhoneNumber(phoneNumber: String)
    fun changeRole(role: UserRoleModel)
    fun changeBlocked(blocked: Boolean)
    fun onSave()
}