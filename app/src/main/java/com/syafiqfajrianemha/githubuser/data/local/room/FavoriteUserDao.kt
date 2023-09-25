package com.syafiqfajrianemha.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.syafiqfajrianemha.githubuser.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorites WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>

    @Insert
    fun insertFavoriteUser(favUser: FavoriteUserEntity)

    @Delete
    fun deleteFavoriteUser(favUser: FavoriteUserEntity)
}