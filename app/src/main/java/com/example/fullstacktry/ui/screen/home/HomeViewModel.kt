package com.example.fullstacktry.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fullstacktry.FullStackApplication
import com.example.fullstacktry.data.FullStackRepository
import com.example.fullstacktry.ui.common.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(private val fullStackRepository: FullStackRepository) : ViewModel() {

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    private val _isLoading  = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getUserList()
    }

     fun getUserList() {
        viewModelScope.launch {
            _isLoading.value = true
            uiState = HomeUiState.Loading
            uiState = try {
                val result = fullStackRepository.getAllProfiles()
                _isLoading.value = false
                HomeUiState.Success(result)
            } catch (e: IOException) {
                _isLoading.value = false
                HomeUiState.Error
            } catch (e: HttpException) {
                _isLoading.value = false
                HomeUiState.Error
            }
        }
    }

    fun deleteProfile(id:Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                fullStackRepository.deleteProfile(id)
                _isLoading.value = false

            }catch (e: IOException) {
                _isLoading.value = false
                HomeUiState.Error
            } catch (e: HttpException) {
                _isLoading.value = false
                HomeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FullStackApplication)
                val fullStackRepository = application.container.fullStackRepository
                HomeViewModel(fullStackRepository = fullStackRepository)
            }
        }
    }
}