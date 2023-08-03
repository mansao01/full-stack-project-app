package com.example.fullstacktry.network.response

import com.google.gson.annotations.SerializedName

data class GetProfileByIdResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
