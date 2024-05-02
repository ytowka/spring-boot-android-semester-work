package com.danilkha.conentfrientdsclient.features.topics.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.features.topics.domain.usecase.GetTopicsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TopicViewModel(
    private val getTopicsUseCase: GetTopicsUseCase
) :ViewModel() {

    val state: StateFlow<TopicListState> = flow {
        val state = TopicListState(getTopicsUseCase().map { it.toTopicModel() })
        emit(state)
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = TopicListState())
}