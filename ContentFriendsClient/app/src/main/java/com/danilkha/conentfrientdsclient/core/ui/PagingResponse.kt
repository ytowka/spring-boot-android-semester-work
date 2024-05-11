package com.danilkha.conentfrientdsclient.core.ui

data class PagingResponse<T>(
    val data: List<T>,
    val page: Int,
    val hasNextPage: Boolean,
) {
}