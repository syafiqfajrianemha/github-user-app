package com.syafiqfajrianemha.githubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafiqfajrianemha.githubuser.data.remote.response.ItemsItem
import com.syafiqfajrianemha.githubuser.databinding.ActivityFavoriteUserBinding
import com.syafiqfajrianemha.githubuser.helper.ViewModelFactory
import com.syafiqfajrianemha.githubuser.ui.adapter.UserAdapter
import com.syafiqfajrianemha.githubuser.ui.viewmodel.FavoriteUserViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorites"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvUserFavorite.layoutManager = LinearLayoutManager(this)

        favoriteUserViewModel.getAllFavoriteUser().observe(this) { favUsers ->
            if (favUsers != null) {
                val items = arrayListOf<ItemsItem>()
                favUsers.map {
                    val item = ItemsItem(it.username, it.avatarUrl)
                    items.add(item)
                }
                binding.rvUserFavorite.adapter = UserAdapter(items)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}