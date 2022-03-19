package com.juple.githubuser2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.juple.githubuser2.data.User
import com.juple.githubuser2.databinding.ListUserBinding

class SearchAdapter :
    RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {

    private var listUser = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addList(user: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private var binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            Glide.with(binding.root)
                .load(user.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(binding.imgItemPhoto)
            binding.tvItemUsername.text = user.login
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}