package com.danilkha.conentfrientdsclient.features.content.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danilkha.conentfrientdsclient.R
import com.danilkha.conentfrientdsclient.core.ui.SearchTopBar
import com.danilkha.conentfrientdsclient.core.ui.TopBar
import com.danilkha.conentfrientdsclient.core.ui.rememberPageableListState
import com.danilkha.conentfrientdsclient.features.topics.ui.Header
import com.danilkha.conentfrientdsclient.features.topics.ui.TopicListItem

@Composable
fun ContentListScreen(
    viewModel: ContentListViewModel,
    onContentClick: (ContentModel) -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    val pagingListState = rememberPageableListState(
        state = state.currentPagerState,
        nextPageRequest = viewModel::getNextPage
    )

    Column {
        SearchTopBar(
            actionIcon = Icons.Filled.ArrowBack,
            onActionClick = onBack,
            text = state.searchQuery,
            onQueryChanged = viewModel::onQueryChange
        )

        LazyColumn(
            state = pagingListState,
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if(state.searchQuery.isEmpty()){
                item {
                    Column {
                        Header(stringResource(R.string.recomend_to_you))
                        if (state.recommendedContent.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .height(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.review_more_in_category_for_recomendations),
                                )
                            }
                        } else {
                            RecomendedContentList(
                                items = state.recommendedContent,
                                onClick = onContentClick,
                            )
                        }
                    }
                }
            }
            item {
                Header(stringResource(R.string.topic, state.topicName))
            }
            items(state.currentPagerState.list, key = { it.id }) {
                ContentListItem(
                    contentModel = it,
                    onClick = { onContentClick(it) }
                )
            }
        }
    }
}

@Composable
fun ContentListItem(
    contentModel: ContentModel,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            model = contentModel.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
                .fillMaxWidth()
                .height(50.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            text = contentModel.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
        )

        Text(
            modifier = Modifier
                .padding(8.dp)
                .background(color = MarkColors.getMarkColor(contentModel.avgMark ?: 0f), shape = CircleShape)
                .padding(8.dp)
                .align(Alignment.TopEnd),
            text = ContentUtils.formatMark(contentModel.avgMark ?: 0f),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Black,
            ),
        )
    }

}

