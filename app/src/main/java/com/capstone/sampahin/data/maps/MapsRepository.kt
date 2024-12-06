package com.capstone.sampahin.data.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.data.api.ApiService
import com.capstone.sampahin.data.register.RegisterRequest

class MapsRepository private constructor(private val apiService: ApiService) {

    private val _mapsResult = MutableLiveData<Result<MapsResponse>>()
    val mapsResult: LiveData<Result<MapsResponse>> get() = _mapsResult

    suspend fun getMaps(address: String, radius: Int) {
        _mapsResult.postValue(Result.Loading)
        try {
            val bodyReq = MapsRequest(address, radius)
            val response = apiService.findPlaces(bodyReq)
            _mapsResult.postValue(Result.Success(response))
        } catch (e: Exception) {
            _mapsResult.postValue(Result.Error(e.message ?: "Terjadi kesalahan"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MapsRepository? = null

        fun getInstance(apiService: ApiService): MapsRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = INSTANCE ?: MapsRepository(apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}
