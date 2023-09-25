package com.syafiqfajrianemha.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syafiqfajrianemha.githubuser.data.FavoriteUserRepository
import com.syafiqfajrianemha.githubuser.data.local.entity.FavoriteUserEntity

class FavoriteUserViewModel(private val repository: FavoriteUserRepository) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getAllFavoriteUser() = repository.getAllFavoriteUser()

    fun insertFavoriteUser(favUser: FavoriteUserEntity) {
        repository.insertFavoriteUser(favUser)
        _isFavorite.value = true
    }

    fun deleteFavoriteUser(favUser: FavoriteUserEntity) {
        repository.deleteFavoriteUser(favUser)
        _isFavorite.value = false
    }

    fun isFavorite(user: String) = repository.isFavoriteUser(user)
}