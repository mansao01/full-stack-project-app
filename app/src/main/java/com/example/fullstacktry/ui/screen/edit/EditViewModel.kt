package com.example.fullstacktry.ui.screen.edit

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
import com.example.fullstacktry.network.request.UpdateProfileRequest
import com.example.fullstacktry.ui.common.UpdateUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class EditViewModel(private val fullStackRepository: FullStackRepository) : ViewModel() {

    var uiState: UpdateUiState by mutableStateOf(UpdateUiState.Loading)
        private set


    fun getProfileById(id:Int){
        viewModelScope.launch {
            uiState = UpdateUiState.Loading
            uiState = try {
                val result = fullStackRepository.getProfileById(id)
                UpdateUiState.StandBy(result)
            }catch (e: IOException) {
                UpdateUiState.ErrorGetProfileData
            } catch (e: HttpException) {
                UpdateUiState.ErrorGetProfileData
            }

        }
    }

    fun updateProfileData(updateProfileRequest: UpdateProfileRequest){
        viewModelScope.launch {
            uiState = UpdateUiState.Loading
            uiState = try {
                val result = fullStackRepository.updateProfile(updateProfileRequest)
                UpdateUiState.Success(result)
            }catch (e: IOException) {
                UpdateUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                UpdateUiState.Error(e.message.toString())
            }
        }
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FullStackApplication)
                val fullStackRepository = application.container.fullStackRepository
                EditViewModel(fullStackRepository = fullStackRepository)
            }
        }
    }
}