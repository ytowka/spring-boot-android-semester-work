package com.danilkha.conentfrientdsclient.features.users.ui.info

import androidx.compose.runtime.Immutable
import com.danilkha.conentfrientdsclient.core.ui.PagingState
import com.danilkha.conentfrientdsclient.features.review.ui.ReviewCard
import com.danilkha.conentfrientdsclient.features.users.ui.UserModel
import java.util.concurrent.Flow

@Immutable
data class UserInfoState(
    val userModel: UserModel? = null,
    val matchScore: Float? = null,
    val reviewListState: PagingState<ReviewCard> = PagingState.initial()

)