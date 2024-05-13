package com.danilkha.conentfrientdsclient.features.topics.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.features.content.domain.usecase.GetRecommendedFeedUseCase
import com.danilkha.conentfrientdsclient.features.content.ui.toContentModel
import com.danilkha.conentfrientdsclient.features.topics.domain.usecase.GetTopicsUseCase
import kotlinx.coroutines.flow.*
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TopicViewModel(
    private val getTopicsUseCase: GetTopicsUseCase,
    private val getRecommendedFeedUseCase: GetRecommendedFeedUseCase
) :ViewModel() {


    val state: StateFlow<TopicListState> = combine(
        flow { emit(getTopicsUseCase()) },
        flow { emit(getRecommendedFeedUseCase()) }
    ){ topics, feed ->
        TopicListState(
            topics = topics.getOrElse { emptyList() }.map { it.toTopicModel() },
            recommendedContent = feed.getOrElse { emptyList() }.map { it.toContentModel() }
        )
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = TopicListState())


}