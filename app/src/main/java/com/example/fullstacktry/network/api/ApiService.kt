package com.example.fullstacktry.network.api

import com.example.fullstacktry.network.request.PostProfileRequest
import com.example.fullstacktry.network.response.DeleteProfileResponse
import com.example.fullstacktry.network.response.PostProfileResponse
import com.example.fullstacktry.network.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("profiles")
    suspend fun getProfiles(): UserResponse

    @POST("postProfile")
    suspend fun postProfile(
//        @Field("name") name: String,
//        @Field("age") age: Int,
//        @Field("address") address: String
        @Body post: PostProfileRequest
    ): PostProfileResponse?

    @DELETE("profile/{id}")
    suspend fun deleteProfile(
        @Path("id") id:Int
    ):DeleteProfileResponse

}