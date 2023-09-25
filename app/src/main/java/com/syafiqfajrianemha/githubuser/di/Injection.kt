package com.syafiqfajrianemha.githubuser.di

import android.content.Context
import com.syafiqfajrianemha.githubuser.data.FavoriteUserRepository
import com.syafiqfajrianemha.githubuser.data.local.room.AppDatabase
import com.syafiqfajrianemha.githubuser.data.remote.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = AppDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        return FavoriteUserRepository.getInstance(apiService, dao)
    }
}