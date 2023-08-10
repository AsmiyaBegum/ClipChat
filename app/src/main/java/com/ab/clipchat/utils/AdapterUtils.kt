package com.ab.clipchat.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ab.clipchat.R
import com.ab.clipchat.databinding.CategoryListRowBinding
import com.ab.clipchat.databinding.ClipCategoryListBinding
import com.ab.clipchat.databinding.GithubUserListRowBinding
import com.ab.clipchat.model.Category
import com.ab.clipchat.model.GitHubUser
import com.ab.clipchat.model.StreamDetails
import com.ab.clipchat.model.VideoStreamCategory
import com.ab.clipchat.ui.videoclips.StreamDetailsAdapter
import com.ab.clipchat.ui.videoclips.StreamDetailsViewHolder
import com.bumptech.glide.Glide

object AdapterUtils {

    fun updateClipCategoryListAdapter(
        streamCategoryList: List<Category>,
    ): GenericAdapter<Category, CategoryListRowBinding, List<Unit>> {

        val placeHolderImages : Map<String, Int> = mapOf(
            "Entertainment" to R.drawable.ic_movies,
            "Astrology" to R.drawable.ic_astrology,
            "Fitness" to R.drawable.ic_meditation,
            "Fashion" to R.drawable.ic_frock,
            "Cooking" to R.drawable.ic_cooking,
            "Doctor" to R.drawable.ic_doctor
        )
        val adapter = GenericAdapter(
            R.layout.category_list_row,
            object :
                GenericAdapterInteraction<Category, CategoryListRowBinding, List<Unit>>() {

                override fun bindingViewHolder(
                    binding: CategoryListRowBinding,
                    data: Category,
                    holder: GenericAdapter.GenericViewHolder<Category, CategoryListRowBinding, List<Unit>>,
                    additionalData: List<Unit>?
                ) {
                    binding.categoryName.text = data.categoryName
                    Glide.with(binding.root)
                        .load(data.categoryPictureUrl)
                        .placeholder(placeHolderImages[data.categoryName]!!)
                        .into(binding.categoryImage)
                }


                override fun onClicked(
                    data: Category,
                    binding: CategoryListRowBinding
                ) {
                    //override fun not implemented
                }
            })
        adapter.addItems(streamCategoryList)

        return adapter
    }

    fun setUpVideoStreamCategoryListAdapter(streamCategoryList : List<VideoStreamCategory>, delegate: StreamDetailsViewHolder.StreamDetailsDelegate) : GenericAdapter<VideoStreamCategory, ClipCategoryListBinding,List<Unit>>{

        val adapter = GenericAdapter(R.layout.clip_category_list,object : GenericAdapterInteraction<VideoStreamCategory, ClipCategoryListBinding,List<Unit>>(){

            override fun bindingViewHolder(
                binding: ClipCategoryListBinding,
                data: VideoStreamCategory,
                holder: GenericAdapter.GenericViewHolder<VideoStreamCategory, ClipCategoryListBinding, List<Unit>>,
                additionalData: List<Unit>?
            ) {
                binding.clipCategoryName.text = data.streamName
                updateStreamList(data.streamList,binding)
            }


            private fun updateStreamList(streamList : List<StreamDetails>, binding: ClipCategoryListBinding){
                binding.videoClips.layoutManager = LinearLayoutManager(binding.root.context,
                    RecyclerView.HORIZONTAL,false)
                binding.videoClips.adapter = StreamDetailsAdapter(streamList,delegate)
            }

            override fun onClicked(data: VideoStreamCategory, binding: ClipCategoryListBinding) {
                //override fun not implemented
            }
        })
        adapter.addItems(streamCategoryList)

        return adapter
    }


    fun updateGitHubUserList(userList: List<GitHubUser>,delegate : GitHubUserDelegate): GenericAdapter<GitHubUser, GithubUserListRowBinding, List<Unit>> {

        val adapter = GenericAdapter(R.layout.github_user_list_row,
            object : GenericAdapterInteraction<GitHubUser, GithubUserListRowBinding, List<Unit>>() {

                override fun bindingViewHolder(
                    binding: GithubUserListRowBinding,
                    data: GitHubUser,
                    holder: GenericAdapter.GenericViewHolder<GitHubUser, GithubUserListRowBinding, List<Unit>>,
                    additionalData: List<Unit>?
                ) {
                    binding.usernameOverlay.text = data.login
                    Glide.with(binding.root)
                        .load(data.avatarUrl)
                        .into(binding.userProfile)
                }


                override fun onClicked(
                    data: GitHubUser,
                    binding: GithubUserListRowBinding
                ) {
                   delegate.selectedGitHubUser(data.htmlUrl)
                }
            })
        adapter.addItems(userList)

        return adapter
    }

    interface GitHubUserDelegate{
        fun selectedGitHubUser(url : String)
    }
}