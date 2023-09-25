package com.syafiqfajrianemha.githubuser.data

import androidx.lifecycle.LiveData
import com.syafiqfajrianemha.githubuser.data.local.entity.FavoriteUserEntity
import com.syafiqfajrianemha.githubuser.data.local.room.FavoriteUserDao
import com.syafiqfajrianemha.githubuser.data.remote.retrofit.ApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao,
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
) {

    fun setSearchUser(keyword: String) = apiService.searchUser(keyword)

    fun setDetailUser(username: String) = apiService.detailUser(username)

    fun setUserFollowers(username: String) = apiService.listFollowers(username)

    fun setUserFollowing(username: String) = apiService.listFollowing(username)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>> = favoriteUserDao.getAllFavoriteUser()

    fun isFavoriteUser(username: String) = favoriteUserDao.getFavoriteUserByUsername(username)

    fun insertFavoriteUser(favUser: FavoriteUserEntity) {
        executorService.execute { favoriteUserDao.insertFavoriteUser(favUser) }
    }

    fun deleteFavoriteUser(username: FavoriteUserEntity) {
        executorService.execute { favoriteUserDao.deleteFavoriteUser(username) }
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null

        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(apiService, favoriteUserDao)
            }.also { instance = it }
    }
}