package com.example.fullstacktry.ui.screen.addscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.FullStackApplication
import com.example.fullstacktry.data.FullStackRepository
import com.example.fullstacktry.network.request.PostProfileRequest
import com.example.fullstacktry.ui.common.AddUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AddViewModel(private val fullStackRepository: FullStackRepository) : ViewModel() {

    var uiState: AddUiState by mutableStateOf(AddUiState.StandBy)
        private set

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getUiState(){
        uiState = AddUiState.StandBy
    }

    fun postProfileData(postProfileRequest: PostProfileRequest) {
        viewModelScope.launch {
            uiState = AddUiState.Loading
            _isLoading.value = true
            uiState = try {
//                val result = fullStackRepository.postProfile(name, age, address)
                val result = fullStackRepository.postProfile(postProfileRequest)
                _isLoading.value = false
                AddUiState.Success(result!!)
            }catch (e: IOException) {
                _isLoading.value = false
                AddUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                _isLoading.value = false
                AddUiState.Error(e.message.toString())
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FullStackApplication)
                val fullStackRepository = application.container.fullStackRepository
                AddViewModel(fullStackRepository = fullStackRepository)
            }
        }
    }

}