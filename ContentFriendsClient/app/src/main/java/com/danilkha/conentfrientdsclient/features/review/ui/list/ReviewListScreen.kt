package com.danilkha.conentfrientdsclient.features.review.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danilkha.conentfrientdsclient.core.ui.PagingState
import com.danilkha.conentfrientdsclient.core.ui.TopBar
import com.danilkha.conentfrientdsclient.core.ui.rememberPageableListState
import com.danilkha.conentfrientdsclient.features.content.ui.ContentUtils
import com.danilkha.conentfrientdsclient.features.content.ui.MarkColors
import com.danilkha.conentfrientdsclient.features.review.ui.ReviewCard

@Composable
fun ReviewListScreen(
    viewModel: ReviewListViewModel,
    contentName: String,
    onBack: () -> Unit,
    onWriteReview: () -> Unit,
    onCardClick: (ReviewCard) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()


    Column {
        TopBar(
            actionIcon = Icons.Filled.ArrowBack,
            onActionClick = { onBack() },
            text = contentName,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            ReviewList(
                modifier = Modifier.fillMaxSize(1f),
                pagingState = state.listState,
                nextPageRequest = viewModel::getNextPage,
                onClick = onCardClick,
            )
            FloatingActionButton(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomEnd),
                onClick = onWriteReview,
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        }
    }


}

@Composable
fun ReviewList(
    modifier: Modifier = Modifier,
    pagingState: PagingState<ReviewCard>,
    nextPageRequest: () -> Unit,
    onClick: (ReviewCard) -> Unit,
){

    val listState = rememberPageableListState(pagingState, nextPageRequest)

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pagingState.list, key = { "${it.reviewModel.contentId}${it.reviewUserInfo.userId}" }){
            ReviewCard(it, onClick = { onClick(it) })
        }
        item {
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@Composable
fun ReviewCard(
    reviewCard: ReviewCard,
    onClick: () -> Unit,
){
    Card(
        onClick = onClick,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val placeholder =  rememberVectorPainter(Icons.Filled.AccountCircle)
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    model = reviewCard.reviewUserInfo.userAvatarUrl,
                    contentDescription = "user_avatar",
                    contentScale = ContentScale.Crop,
                    placeholder = placeholder,
                    fallback = placeholder,
                    error = placeholder
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "@${reviewCard.reviewUserInfo.userName}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.weight(1f))
                MarkBadge(
                        mark = reviewCard.reviewModel.mark
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = reviewCard.reviewModel.text,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun MarkBadge(
    modifier: Modifier = Modifier,
    mark: Number?
){

    Text(
        modifier = modifier
            .size(50.dp)
            .background(
                color = MarkColors.getMarkColor(mark ?: 0f),
                shape = CircleShape
            )
            .wrapContentSize(Alignment.Center)
            .padding(8.dp),
        text = ContentUtils.formatMark(mark ?: 0f),
        textAlign = TextAlign.Center,
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        ),
    )
}