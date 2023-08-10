package com.ab.clipchat.ui.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ab.clipchat.databinding.GithubUserListRowBinding
import com.ab.clipchat.model.GitHubUser
import com.ab.clipchat.utils.AdapterUtils
import com.ab.clipchat.utils.Utils.capitalizeFirstChar
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding.view.clicks
import rx.android.schedulers.AndroidSchedulers

class GitHubUserPagingAdapter(val delegate : AdapterUtils.GitHubUserDelegate) : PagingDataAdapter<GitHubUser, GitHubUserPagingAdapter.UserViewHolder>(diffCallback) {

    inner class UserViewHolder(val binding : GithubUserListRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = getItem(position)!!
        holder.binding.usernameOverlay.text = data.login.capitalizeFirstChar()

        Glide.with(holder.binding.root)
            .load(data.avatarUrl)
            .into( holder.binding.userProfile)

        holder.itemView.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                delegate.selectedGitHubUser(data.htmlUrl)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(GithubUserListRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    companion object{
        val diffCallback = object  : DiffUtil.ItemCallback<GitHubUser>(){
            override fun areContentsTheSame(oldItem: GitHubUser, newItem: GitHubUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: GitHubUser, newItem: GitHubUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}