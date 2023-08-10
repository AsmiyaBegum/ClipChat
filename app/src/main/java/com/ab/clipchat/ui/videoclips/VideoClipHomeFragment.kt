package com.ab.clipchat.ui.videoclips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ab.clipchat.databinding.FragmentHomeBinding
import com.ab.clipchat.model.Category
import com.ab.clipchat.model.StreamDetails
import com.ab.clipchat.model.VideoStreamCategory
import com.ab.clipchat.utils.AdapterUtils
import com.ab.clipchat.utils.Utils
import rx.android.schedulers.AndroidSchedulers

class VideoClipHomeFragment : Fragment(),StreamDetailsViewHolder.StreamDetailsDelegate {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    lateinit var viewModel: VideoClipsViewModel

    private fun bind(){
        viewModel.output.videoCategoryLst()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateCategoryList(it)
            }

        viewModel.output.videoCategorywiseVideoClips()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateCategoryWiseClips(it)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoClipsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        updateHomeUI()
        Utils.checkDeviceOnline(requireContext())
    }

    private fun updateHomeUI(){
        viewModel.input.getVideoCategoryList()
        viewModel.input.getCategorywiseVideoClips()
    }

    private fun updateCategoryList(categoryList : List<Category>){
        binding.categoryList.layoutManager = GridLayoutManager(requireContext(),3)
        binding.categoryList.adapter = AdapterUtils.updateClipCategoryListAdapter(categoryList)
    }

    private fun updateCategoryWiseClips(streamList : List<VideoStreamCategory>){
       binding.videoList.layoutManager = LinearLayoutManager(requireContext())
       binding.videoList.adapter = AdapterUtils.setUpVideoStreamCategoryListAdapter(streamList,this)
    }

    override fun selectedStreamVideo(data: StreamDetails) {
        // Do play selected video
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}