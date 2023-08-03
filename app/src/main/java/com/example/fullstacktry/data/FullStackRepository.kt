package com.example.fullstacktry.data

import com.example.fullstacktry.network.api.ApiService
import com.example.fullstacktry.network.request.PostProfileRequest
import com.example.fullstacktry.network.request.UpdateProfileRequest
import com.example.fullstacktry.network.response.DeleteProfileResponse
import com.example.fullstacktry.network.response.GetProfileByIdResponse
import com.example.fullstacktry.network.response.PostProfileResponse
import com.example.fullstacktry.network.response.UpdateProfileResponse
import com.example.fullstacktry.network.response.UserResponse

interface FullStackRepository {
    suspend fun getAllProfiles(): UserResponse

    suspend fun getProfileById(id:Int): GetProfileByIdResponse
    suspend fun postProfile(postProfileRequest: PostProfileRequest): PostProfileResponse?
//    suspend fun postProfile(name: String, age: Int, address: String): PostProfileResponse

    suspend fun deleteProfile(id: Int): DeleteProfileResponse

    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse
}

class NetworkFullStackRepository(
    private val apiService: ApiService
) : FullStackRepository {
    override suspend fun getAllProfiles(): UserResponse {
        return apiService.getProfiles()
    }

    override suspend fun getProfileById(id:Int): GetProfileByIdResponse {
        return apiService.getProfileById(id)
    }

    override suspend fun postProfile(postProfileRequest: PostProfileRequest): PostProfileResponse? {
        return apiService.postProfile(postProfileRequest)
    }

    //    override suspend fun postProfile(name: String, age: Int, address: String): PostProfileResponse {
//        return apiService.postProfile(name, age, address)
//    }

    override suspend fun deleteProfile(id: Int): DeleteProfileResponse =
        apiService.deleteProfile(id)

    override suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse =
        apiService.updateProfile(updateProfileRequest)

}