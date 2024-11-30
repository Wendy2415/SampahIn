package com.capstone.sampahin.data.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(
	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
