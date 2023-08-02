package com.example.fullstacktry.ui.common

import com.example.fullstacktry.network.response.PostProfileResponse
import com.example.fullstacktry.network.response.UserResponse

sealed interface HomeUiState {
    data class Success(val profile: UserResponse) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

sealed interface AddUiState {
    data class Success(val postData: PostProfileResponse) : AddUiState
    object Error : AddUiState
    object Loading : AddUiState
}