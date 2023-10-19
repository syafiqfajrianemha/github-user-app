package com.syafiqfajrianemha.githubuser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafiqfajrianemha.githubuser.R
import com.syafiqfajrianemha.githubuser.databinding.ActivityMainBinding
import com.syafiqfajrianemha.githubuser.helper.ViewModelFactory
import com.syafiqfajrianemha.githubuser.ui.adapter.UserAdapter
import com.syafiqfajrianemha.githubuser.ui.favorite.FavoriteUserActivity
import com.syafiqfajrianemha.githubuser.ui.settings.SettingsActivity
import com.syafiqfajrianemha.githubuser.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>() {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel.userList.observe(this) { user ->
            binding.rvUser.adapter = UserAdapter(user)
        }

        mainViewModel.userNotFound.observe(this) {
            showUserNotFound(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.svQ.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                val keyword = q.trim()

                mainViewModel.setSearchUser(keyword)
                hideKeyboard()
                binding.svQ.clearFocus()

                return true
            }

            override fun onQueryTextChange(q: String): Boolean {
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                val moveToFavorite = Intent(this, FavoriteUserActivity::class.java)
                startActivity(moveToFavorite)
            }

            R.id.settings_menu -> {
                val moveToSettingsMenu = Intent(this, SettingsActivity::class.java)
                startActivity(moveToSettingsMenu)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showUserNotFound(it: Boolean) {
        binding.dataEmpty.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}