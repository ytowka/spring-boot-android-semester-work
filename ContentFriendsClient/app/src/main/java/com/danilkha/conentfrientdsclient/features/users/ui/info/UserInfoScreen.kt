package com.danilkha.conentfrientdsclient.features.users.ui.info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danilkha.conentfrientdsclient.R
import com.danilkha.conentfrientdsclient.core.ui.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserInfoScreen(
    onBack: () -> Unit,
    viewModel: UserInfoViewModel = koinViewModel(),
){
    val state by viewModel.uiState.collectAsState()

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
                val placeholder = rememberVectorPainter(Icons.Filled.AccountCircle)
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    model = state.userModel?.avatarUrl,
                    contentDescription = "user_avatar",
                    contentScale = ContentScale.Crop,
                    placeholder = placeholder,
                    fallback = placeholder,
                    error = placeholder
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "@${state.userModel?.login ?: ""}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = state.userModel?.fullName.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }

        }
    }
}