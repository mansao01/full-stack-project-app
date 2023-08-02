package com.example.fullstacktry.network.response


data class UserResponse(

	val msg: String,

	val data: List<DataItem>
)

data class DataItem(

	val createdAt: String,

	val address: String,

	val name: String,

	val id: Int,

	val age: Int,

	val updatedAt: String
)
