package com.danilkha.conentfrientdsclient.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danilkha.conentfrientdsclient.R

object TopBarDefaults {
    val height = 56.dp
}

@Composable
fun GenericTopBar(
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit,
    centerContent: @Composable RowScope.() -> Unit,
    endContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TopBarDefaults.height)
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (actionIcon != null) {
            IconButton(
                onClick = onActionClick,
            ) {
                Icon(actionIcon, contentDescription = "")
            }
        }
        Spacer(Modifier.size(10.dp))
        centerContent()
        if (endContent != null) {
            endContent()
        }
    }
}

@Composable
fun TopBar(
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit,
    text: String? = null,
    endContent: @Composable (() -> Unit)? = null,
) {
    GenericTopBar(
        actionIcon = actionIcon,
        onActionClick = onActionClick,
        centerContent = {
            if (text != null) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        endContent = endContent
    )
}

@Composable
fun SearchTopBar(
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit,
    text: String = "",
    onQueryChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    GenericTopBar(
        actionIcon = actionIcon,
        onActionClick = onActionClick,
        centerContent = {
            BasicTextField(
                modifier = Modifier
                    .weight(1f),
                value = text,
                onValueChange = onQueryChanged,
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.padding(5.dp)) {
                        Box(contentAlignment = Alignment.CenterStart) {
                            if(text.isEmpty()){
                                Text(text = stringResource(R.string.search))
                            }
                            innerTextField()
                        }
                        IconButton(
                            onClick = { onQueryChanged("") },
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "")
                        }
                    }
                }
            )
        },
        endContent = {
            IconButton(
                onClick = { focusManager.clearFocus() },
            ) {
                Icon(Icons.Filled.Search, contentDescription = "")
            }
        }
    )
}