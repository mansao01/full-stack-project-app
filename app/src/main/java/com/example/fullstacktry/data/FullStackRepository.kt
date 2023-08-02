package com.example.fullstacktry.data

import com.example.fullstacktry.network.api.ApiService
import com.example.fullstacktry.network.request.PostProfileRequest
import com.example.fullstacktry.network.response.PostProfileResponse
import com.example.fullstacktry.network.response.UserResponse

interface FullStackRepository {
    suspend fun getAllProfiles(): UserResponse
    suspend fun postProfile(postProfileRequest:PostProfileRequest): PostProfileResponse?
//    suspend fun postProfile(name: String, age: Int, address: String): PostProfileResponse
}

class NetworkFullStackRepository(
    private val apiService: ApiService
) : FullStackRepository {
    override suspend fun getAllProfiles(): UserResponse {
        return apiService.getProfiles()
    }

    override suspend fun postProfile(postProfileRequest:PostProfileRequest): PostProfileResponse? {
        return apiService.postProfile(postProfileRequest)
    }
//    override suspend fun postProfile(name: String, age: Int, address: String): PostProfileResponse {
//        return apiService.postProfile(name, age, address)
//    }

}