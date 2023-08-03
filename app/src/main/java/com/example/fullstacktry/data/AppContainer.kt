package com.example.fullstacktry.data

import com.example.fullstacktry.network.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val fullStackRepository:FullStackRepository
}
class DefaultAppContainer : AppContainer {
//    base url depend from {pc's ip address}:8000
    private val baseUrl = "http://192.168.18.162:8000/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    override val fullStackRepository:FullStackRepository by lazy {
        NetworkFullStackRepository(retrofitService)
    }

}