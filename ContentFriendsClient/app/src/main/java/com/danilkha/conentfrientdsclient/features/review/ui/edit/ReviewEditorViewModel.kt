package com.danilkha.conentfrientdsclient.features.review.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.features.content.domain.usecase.GetContentByIdUseCase
import com.danilkha.conentfrientdsclient.features.content.ui.toContentModel
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewRequestDto
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.DeleteReviewUseCase
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.EditReviewUseCase
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.GetReviewByUserAndContentUseCase
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.WriteReviewUseCase
import com.danilkha.conentfrientdsclient.features.review.ui.toReviewModel
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetMeUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ReviewEditorViewModel(
    val editReviewUseCase: EditReviewUseCase,
    val writeReviewUseCase: WriteReviewUseCase,
    val getContentByIdUseCase: GetContentByIdUseCase,
    val deleteReviewUseCase: DeleteReviewUseCase,
    val getReviewByUserAndContentUseCase: GetReviewByUserAndContentUseCase,
    val getMeUseCase: GetMeUseCase,
    val contentId: Long
) : ViewModel() {

    val _uiState = MutableStateFlow(EditReviewState())
    val uiState: StateFlow<EditReviewState> = _uiState.asStateFlow()

    val updateFlow = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            getContentByIdUseCase(contentId).onSuccess { content ->
                _uiState.update { it.copy(content = content.toContentModel()) }
            }
        }
        getWrittenReview()
    }


    fun editMark(mark: Int){
        _uiState.update {
            it.copy(mark = mark)
        }
    }

    fun editText(text: String){
        _uiState.update {
            it.copy(text = text)
        }
    }

    fun delete(){
        val mode = uiState.value.mode
        if (mode is EditReviewMode.Edit){
            viewModelScope.launch {
                deleteReviewUseCase(mode.reviewModel.id).onSuccess {
                    updateFlow.emit(Unit)
                }
            }
        }
    }

    fun save() {
        if(uiState.value.isValid){
            viewModelScope.launch {
                val request = ReviewRequestDto(
                    contentId = contentId,
                    mark = uiState.value.mark ?: 0,
                    text = uiState.value.text
                )
                when(_uiState.value.mode){
                    is EditReviewMode.Edit -> editReviewUseCase(request)
                    EditReviewMode.New -> writeReviewUseCase(request)
                    EditReviewMode.Pending -> Result.failure(Exception("not valid"))
                }.onSuccess {
                    updateFlow.emit(Unit)
                }
            }

        }
    }

    private fun getWrittenReview(){
        viewModelScope.launch {
            val me = getMeUseCase().getOrElse { return@launch }
            val params = GetReviewByUserAndContentUseCase.Params(
                userId = me.id,
                contentId = contentId
            )
            getReviewByUserAndContentUseCase(params)
                .onSuccess { review ->
                    _uiState.update {
                        it.copy(
                            mode = EditReviewMode.Edit(reviewModel = review.toReviewModel().reviewModel),
                            text = review.text,
                            mark = review.mark
                        )
                    }
                }.onFailure {
                    _uiState.update {
                        it.copy(mode = EditReviewMode.New)
                    }
                }
        }

    }
}