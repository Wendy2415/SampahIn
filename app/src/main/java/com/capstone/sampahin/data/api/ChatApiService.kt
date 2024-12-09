package com.capstone.sampahin.data.api

import com.capstone.sampahin.data.chat.ChatRequest
import com.capstone.sampahin.data.chat.ChatResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApiService {
    @GET("topics")
    suspend fun getTopic(): List<String>

    @GET("questions/<selectedTopic>")
    suspend fun getQuestion(
        @Path("selectedTopic")
        selectedTopic: String
    ): List<String>

    @POST("answer")
    suspend fun answer(
        @Body request: ChatRequest
    ): ChatResponse
}