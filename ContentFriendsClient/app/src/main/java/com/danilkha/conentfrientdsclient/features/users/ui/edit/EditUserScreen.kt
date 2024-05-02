package com.danilkha.conentfrientdsclient.features.users.ui.edit

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danilkha.conentfrientdsclient.R
import com.danilkha.conentfrientdsclient.core.ui.DropDownMenu
import com.danilkha.conentfrientdsclient.core.ui.TopBar
import com.danilkha.conentfrientdsclient.features.users.ui.UserRoleModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditUserScreen(
    onBack: () -> Unit,
    viewModel: EditUserViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.userSaved) {
        if(state.userSaved) {
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxSize()
    ) {
        TopBar(
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onActionClick = onBack,
            text = stringResource(R.string.edit_user)
        )

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val placeholder =  rememberVectorPainter(Icons.Filled.AccountCircle)
                SideEffect {
                    Log.d("debugg", "EditUserScreen() called ${state.initalUser?.avatarUrl}")
                }
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    model = state.initalUser?.avatarUrl,
                    contentDescription = "user_avatar",
                    contentScale = ContentScale.Crop,
                    placeholder = placeholder,
                    fallback = placeholder,
                    error = placeholder
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "@${state.initalUser?.login ?: ""}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = state.initalUser?.id.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.fullName,
                onValueChange = viewModel::changeFullName,
                label = { Text(stringResource(R.string.full_name)) },
                isError = state.fullName.isBlank(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.email,
                onValueChange = viewModel::changeEmail,
                label = { Text(stringResource(R.string.email)) },
                isError = state.email.isBlank(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.phone,
                onValueChange = viewModel::changePhoneNumber,
                label = { Text(stringResource(R.string.phone_number)) },
                isError = state.phone.isBlank(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(10.dp))
            if(state.isMe){
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                ){
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = state.role.name
                    )
                }
            }else{
                var dropdownExpanded by remember { mutableStateOf(false) }
                DropDownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    opened = dropdownExpanded,
                    menuItems = (UserRoleModel.entries - state.role),
                    onStateChange = { dropdownExpanded = it },
                    onItemSelected = { index, item ->
                        dropdownExpanded = false
                        viewModel.changeRole(item)
                    },
                    header = {
                        Text(
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.Center)
                                .fillMaxWidth(),
                            text = state.role.name,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    item = {
                        Text(
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.Center)
                                .fillMaxWidth(),
                            text = it.name)
                    },
                )
            }

            if(!state.isMe){
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedCard{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.isBlocked,
                            onCheckedChange = viewModel::changeBlocked,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(stringResource(R.string.is_blocked))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = viewModel::onSave,
            enabled = state.isChanged,
        ){
            Text(stringResource(R.string.save))
        }
    }
}