package com.capstone.sampahin.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.sampahin.data.api.ApiConfig
import com.capstone.sampahin.data.chat.ChatRequest
import com.capstone.sampahin.data.chat.ChatResponse
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _topics = MutableLiveData<List<ChatRequest>>()
    val topics: LiveData<List<ChatRequest>> get() = _topics

    private var selectedTopic: ChatRequest? = null

    private val _questions = MutableLiveData<List<ChatRequest>>()
    val questions: LiveData<List<ChatRequest>> get() = _questions

    private val _answers = MutableLiveData<ChatResponse?>()
    val answers: LiveData<ChatResponse?> get() = _answers

    // fetch data topics
    fun fetchTopics() {
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getChatApiService()
                val topics = apiService.getTopics()
                _topics.value = topics
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching topics: ${e.message}")
                e.printStackTrace()
                _topics.value = emptyList()
            }
        }
    }

    // fetch data questions
    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getChatApiService()
                val questions = apiService.getQuestions((selectedTopic ?: "").toString())
                _questions.value = questions
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching questions: ${e.message}")
                e.printStackTrace()
                _questions.value = emptyList()
            }
        }
    }

    // fetch data answers
    fun fetchAnswers(request: ChatRequest) {
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getChatApiService()
                val answers = apiService.sendAnswer(request)
                _answers.postValue(answers)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching answers: ${e.message}")
                e.printStackTrace()
                _answers.postValue(null)
            }
        }
    }

    companion object {
        private const val TAG = "ChatViewModel"
    }
}