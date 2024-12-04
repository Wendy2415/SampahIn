package com.capstone.sampahin.data

import android.content.Context
import com.capstone.sampahin.data.api.ApiConfig
import com.capstone.sampahin.data.login.LoginRepository
import com.capstone.sampahin.data.register.RegisterRepository

object Injection {
    fun provideRegisterRepository(context: Context): RegisterRepository {
        val apiService = ApiConfig.getApiService()
        return RegisterRepository.getInstance(apiService)
    }

    fun provideLoginRepository(context: Context): LoginRepository {
        val apiService = ApiConfig.getApiService()
        return LoginRepository.getInstance(
            apiService,
            TokenPreferences.getInstance(context),
            AppDatabase.getInstance(context).userDao()
        )
    }

}
