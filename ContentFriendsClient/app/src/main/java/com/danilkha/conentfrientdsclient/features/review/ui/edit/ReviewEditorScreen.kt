package com.danilkha.conentfrientdsclient.features.review.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danilkha.conentfrientdsclient.R
import com.danilkha.conentfrientdsclient.core.ui.TopBar
import com.danilkha.conentfrientdsclient.features.content.ui.ContentModel
import com.danilkha.conentfrientdsclient.features.content.ui.ContentUtils
import com.danilkha.conentfrientdsclient.features.content.ui.MarkColors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReviewEditorScreen(
    viewModel: ReviewEditorViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.updateFlow.collectLatest {
            onSave()
        }
    }

    Column {
        TopBar(
            actionIcon = Icons.Default.ArrowBack,
            onActionClick = onBack,
            text = stringResource(R.string.writesAbout),
            endContent = if(state.mode is EditReviewMode.Edit) {
                {
                    IconButton(
                        onClick = viewModel::delete
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                        )
                    }
                }
            }else null
        )
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            ReviewEditContentHeader(
                state.content
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.text,
                onValueChange = viewModel::editText,
                placeholder = {
                    Text(
                        text = stringResource(R.string.review_text_field_hint),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f),
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            MarkPicker(
                mark = state.mark ?: 0,
                onMarkChanged = viewModel::editMark
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::save,
                enabled = state.isValid,
            ){
                Text(text = stringResource(R.string.save),)
            }
        }
    }
}

@Composable
private fun ReviewEditContentHeader(
    content: ContentModel?
){
    Box(modifier = Modifier
        .clip(MaterialTheme.shapes.medium)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
        .aspectRatio(3f/2f)
    ) {
        if(content != null){
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = content.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(
                modifier = Modifier
                    .background(brush = Brush.verticalGradient(colors = listOf(Color.Black, Color.Transparent)))
                    .fillMaxWidth()
                    .height(60.dp)
            )
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = content.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

@Composable
fun MarkPicker(
    mark: Int,
    onMarkChanged: (Int) -> Unit
){
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth(),
    ) {
        for(i in 1..10){
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                onClick = { onMarkChanged(i) },
            ){
                val color = MarkColors.getMarkColor(mark)
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "mark",
                    tint = if(i > mark)
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
                    else color,
                )
            }
        }
    }
}