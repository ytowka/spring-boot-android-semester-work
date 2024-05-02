package com.danilkha.conentfrientdsclient.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object NavigationBarDimensions{
    val height = 56.dp
}

interface NavigationBarBuilder{

    fun navigationItem(
        label: String,
        icon: ImageVector,
        onClick: (index: Int) -> Unit,
    )
}

@Composable
fun NavigationBar(
    items: NavigationBarBuilder.() -> Unit,
    selectedItemIndex: Int,
){
    val selectedBackgroundColor = MaterialTheme.colorScheme.primary
    val indexMultiplier by animateFloatAsState(
        targetValue = selectedItemIndex.toFloat(), label = "background slide"
    )
    val cornerSize = 4.dp
    val padding = 4.dp


    val items = remember {
        val builder = object : NavigationBarBuilder {
            val list = mutableListOf<NavigationBarItem>()

            override fun navigationItem(label: String, icon: ImageVector, onClick: (index: Int) -> Unit) {
                list.add(NavigationBarItem(label, icon, onClick))
            }
        }
        items(builder)
        builder.list
    }

    Row(
        modifier = Modifier
            .height(NavigationBarDimensions.height)
            .background(color = MaterialTheme.colorScheme.surface)
            .drawBehind {
                val width = size.width /items.size

                drawRoundRect(
                    color = selectedBackgroundColor,
                    topLeft = Offset(
                        x = width * indexMultiplier + padding.toPx(),
                        y = padding.toPx()
                    ),
                    size = Size(
                        width = width - padding.toPx() * 2,
                        size.height - padding.toPx() * 2,
                    ),
                    cornerRadius = CornerRadius(cornerSize.toPx(), cornerSize.toPx())
                )
            }
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                isSelected = index == selectedItemIndex,
                label = item.label,
                icon = item.icon,
                onClick = { item.onClick(index) }
            )
        }
    }
}

@Immutable
private data class NavigationBarItem(
    val label: String,
    val icon: ImageVector,
    val onClick: (index: Int) -> Unit,
)

@Composable
fun RowScope.NavigationBarItem(
    isSelected: Boolean,
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(2.dp)
            .fillMaxHeight()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() })
            .semantics {
                contentDescription = "navigation_item_$label"
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val iconColor = if(isSelected){
            MaterialTheme.colorScheme.onPrimary
        }else MaterialTheme.colorScheme.onSurface

        Icon(
            contentDescription = null,
            tint = iconColor,
            imageVector = icon,
        )
        if(isSelected){
            Text(
                text = label,
                color = iconColor,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                )
            )
        }
    }
}