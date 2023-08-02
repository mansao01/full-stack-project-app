package com.example.fullstacktry.ui.common

import com.example.fullstacktry.network.response.PostProfileResponse
import com.example.fullstacktry.network.response.UserResponse

sealed interface HomeUiState {
    data class Success(val profile: UserResponse) : HomeUiState
//    data class SuccessDelete(val profile: UserResponse, val message: DeleteProfileResponse) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

sealed interface AddUiState {
    data class Success(val postData: PostProfileResponse) : AddUiState
    data class Error(val message:String) : AddUiState
    object Loading : AddUiState
}