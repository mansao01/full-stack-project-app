package com.example.fullstacktry.network.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("msg")
	val msg: String
)
