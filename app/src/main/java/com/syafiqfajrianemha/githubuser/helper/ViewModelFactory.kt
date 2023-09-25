package com.syafiqfajrianemha.githubuser.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syafiqfajrianemha.githubuser.data.FavoriteUserRepository
import com.syafiqfajrianemha.githubuser.di.Injection
import com.syafiqfajrianemha.githubuser.ui.viewmodel.DetailUserViewModel
import com.syafiqfajrianemha.githubuser.ui.viewmodel.FavoriteUserViewModel
import com.syafiqfajrianemha.githubuser.ui.viewmodel.FollowViewModel
import com.syafiqfajrianemha.githubuser.ui.viewmodel.MainViewModel

class ViewModelFactory private constructor(private val repository: FavoriteUserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}