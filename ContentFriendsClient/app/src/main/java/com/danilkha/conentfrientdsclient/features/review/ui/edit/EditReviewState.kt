package com.danilkha.conentfrientdsclient.features.review.ui.edit

import com.danilkha.conentfrientdsclient.features.review.ui.ReviewModel

data class EditReviewState(
    val mode: EditReviewMode = EditReviewMode.New,
    val mark: Int? = null,
    val text: String = "",
    val contentId: Long = 0
){
    val isValid: Boolean
        get() = when(mode){
            is EditReviewMode.Edit -> {
                (mark != mode.reviewModel.mark ||
                        text != mode.reviewModel.text) && mark != null
            }
            EditReviewMode.New -> {
                mark != null
            }
        }
}

sealed interface EditReviewMode {
    data object New : EditReviewMode
    class Edit(val reviewModel: ReviewModel) : EditReviewMode
}