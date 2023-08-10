package com.ab.clipchat.ui.videoclips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ab.clipchat.databinding.ClipsListLayoutBinding
import com.ab.clipchat.model.StreamDetails
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding.view.clicks

class StreamDetailsAdapter(private val streamDetailList : List<StreamDetails>, private val delegate : StreamDetailsViewHolder.StreamDetailsDelegate)  : RecyclerView.Adapter<StreamDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamDetailsViewHolder {
        val binding = ClipsListLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return StreamDetailsViewHolder(binding,delegate)
    }

    override fun onBindViewHolder(holder: StreamDetailsViewHolder, position: Int) {
        holder.onBind(streamDetailList[position])
    }

    override fun getItemCount(): Int {
        return streamDetailList.size
    }


}


class StreamDetailsViewHolder(private val binding : ClipsListLayoutBinding, private val delegate: StreamDetailsDelegate) : RecyclerView.ViewHolder(binding.root){

    fun onBind(data  : StreamDetails){

        Glide.with(binding.root)
            .load(data.thumbnail)
            .into(binding.imageThumbnail)


        binding.imageThumbnail.clicks()
            .subscribe {
                delegate.selectedStreamVideo(data)
            }

    }


    interface StreamDetailsDelegate{
        fun selectedStreamVideo(data: StreamDetails)
    }
}