package com.example.fullstacktry.network.request

import com.google.gson.annotations.SerializedName

data class PostProfileRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("age")
    val age: Int,

    @field:SerializedName("address")
    val address: String,

)
