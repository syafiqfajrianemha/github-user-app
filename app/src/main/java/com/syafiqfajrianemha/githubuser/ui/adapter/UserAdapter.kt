package com.syafiqfajrianemha.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.syafiqfajrianemha.githubuser.data.remote.response.ItemsItem
import com.syafiqfajrianemha.githubuser.databinding.ItemUserBinding
import com.syafiqfajrianemha.githubuser.ui.detail.DetailUserActivity

class UserAdapter(private val userList: List<ItemsItem>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

//    private var onItemClickCallback: OnItemClickCallback? = null
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
//            binding.root.setOnClickListener {
//                onItemClickCallback?.onItemClicked(user)
//            }

            binding.tvUsername.text = user.username
            binding.ivAvatar.loadImage(user.avatarUrl)

            itemView.setOnClickListener { view ->
                val toDetail = Intent(view.context, DetailUserActivity::class.java)
                toDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, user.username)
                view.context.startActivity(toDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

//    interface OnItemClickCallback {
//        fun onItemClicked(user: ItemsItem)
//    }

    fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .circleCrop()
            .into(this)
    }
}