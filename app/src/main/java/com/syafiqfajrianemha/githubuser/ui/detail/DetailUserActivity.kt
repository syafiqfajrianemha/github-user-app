package com.syafiqfajrianemha.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.syafiqfajrianemha.githubuser.R
import com.syafiqfajrianemha.githubuser.data.local.entity.FavoriteUserEntity
import com.syafiqfajrianemha.githubuser.data.remote.response.DetailUserResponse
import com.syafiqfajrianemha.githubuser.databinding.ActivityDetailUserBinding
import com.syafiqfajrianemha.githubuser.helper.ViewModelFactory
import com.syafiqfajrianemha.githubuser.ui.SectionsPagerAdapter
import com.syafiqfajrianemha.githubuser.ui.viewmodel.DetailUserViewModel
import com.syafiqfajrianemha.githubuser.ui.viewmodel.FavoriteUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailUserViewModel by viewModels<DetailUserViewModel>() {
        ViewModelFactory.getInstance(this)
    }
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel>() {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var favoriteUserEntity: FavoriteUserEntity
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME).toString()

        if (savedInstanceState == null) {
            detailUserViewModel.setDetailUser(username)
        }

        detailUserViewModel.detailUser.observe(this) {
            setUserData(it)
            favoriteUserEntity = FavoriteUserEntity(
                it.username,
                it.avatarUrl
            )
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        sectionsPagerAdapter.username = username

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        favoriteUserViewModel.isFavorite(username).observe(this) { favUser ->
            isFavorite = favUser != null
            changeIcon()
        }

        favoriteUserViewModel.isFavorite.observe(this) {
            isFavorite = it == true
            changeIcon()
        }

        binding.btnFavorite.setOnClickListener {
            if (!isFavorite) {
                favoriteUserViewModel.insertFavoriteUser(favoriteUserEntity)
                Toast.makeText(this, "Favorite added successfully.", Toast.LENGTH_SHORT).show()
            } else {
                favoriteUserViewModel.deleteFavoriteUser(favoriteUserEntity)
                Toast.makeText(this, "Favorite deleted successfully.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/$username")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, null))
        }
    }

    private fun setUserData(user: DetailUserResponse?) {
        if (user != null) {
            binding.tvUsername.text = user.username
            binding.tvName.text = user.name
            binding.tvFollowers.text = "${user.followers} Followers"
            binding.tvFollowing.text = "${user.following} Following"
            Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .circleCrop()
                .into(binding.ivAvatar)
        }
    }

    private fun changeIcon() {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}