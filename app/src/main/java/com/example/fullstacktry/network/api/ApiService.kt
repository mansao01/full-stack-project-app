package com.example.fullstacktry.network.api

import com.example.fullstacktry.network.request.PostProfileRequest
import com.example.fullstacktry.network.response.PostProfileResponse
import com.example.fullstacktry.network.response.UserResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

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

}