package com.danilkha.conentfrientdsclient.features.topics.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danilkha.conentfrientdsclient.R
import com.danilkha.conentfrientdsclient.core.ui.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopicScreen(
    topicsViewModel: TopicViewModel = koinViewModel(),
    onTopicClick: (Long) -> Unit,
) {
    val state by topicsViewModel.state.collectAsState()

    Column {
        TopBar(
            actionIcon = null,
            onActionClick = {},
            text = stringResource(R.string.topics),
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.topics, key = { it.id }){
                TopicListItem(topic = it, onClick = {onTopicClick(it.id)})
            }
        }
    }

}

@Composable
fun TopicListItem(topic: TopicModel, onClick: () -> Unit) {
    Card(
        onClick = onClick
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = topic.imageUrl,
                contentDescription = null
            )
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = topic.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = topic.contentCount.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}