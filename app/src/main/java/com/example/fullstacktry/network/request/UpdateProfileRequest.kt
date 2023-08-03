package com.example.fullstacktry.network.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("age")
    val age: Int,

    @field:SerializedName("address")
    val address: String,
)
