package com.danilkha.conentfrientdsclient.features.topics.ui

import com.danilkha.conentfrientdsclient.features.content.ui.ContentModel


data class TopicListState(
    val topics: List<TopicModel> = listOf(),
    val recommendedContent: List<ContentModel> = listOf()
)