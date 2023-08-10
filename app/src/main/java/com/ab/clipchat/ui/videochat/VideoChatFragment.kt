package com.ab.clipchat.ui.videochat

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.ab.clipchat.R
import com.ab.clipchat.databinding.VideoGroupChatBinding
import com.ab.clipchat.utils.Utils
import com.ab.clipchat.utils.Utils.showVisibility
import com.airbnb.lottie.LottieDrawable
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.AssetDataSource
import com.google.android.exoplayer2.upstream.DataSource

class VideoChatFragment : Fragment() {

    private var _binding: VideoGroupChatBinding? = null
    private lateinit var player1: ExoPlayer
    private lateinit var player2 : ExoPlayer
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = VideoGroupChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
        setupPlayer()
        startAnimation()
        Utils.checkDeviceOnline(requireContext())
    }

    private fun startAnimation(){
        val slideInFromLeftAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.left_slide_in)
        val slideInFromRightAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.right_slide_in)
        startSlideInFromRightAndLeft(slideInFromLeftAnimation,slideInFromRightAnimation)

        startLottieAnimation()
    }

    private fun startSlideInFromRightAndLeft(slideInFromLeftAnimation : Animation,slideInFromRightAnimation : Animation){
        binding.exoPlayerView1.startAnimation(slideInFromLeftAnimation)
        binding.exoPlayerView2.startAnimation(slideInFromRightAnimation)
        Thread.sleep(2000)
        binding.profile1Layout.startAnimation(slideInFromLeftAnimation)
        binding.profile2Layout.startAnimation(slideInFromRightAnimation)
    }

    private fun startLottieAnimation() {
        binding.animationView.setAnimation(R.raw.gift)
        binding.animationView.playAnimation()
        binding.animationView.repeatCount = LottieDrawable.INFINITE


        val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
        val animationViewWidth = binding.animationView.width.toFloat()

        val animator = ObjectAnimator.ofFloat(binding.animationView, "translationX", screenWidth, -animationViewWidth)
        animator.duration = 5000 // Adjust the duration as needed
        animator.start()


        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                val profileViews = listOf(binding.pImg,binding.kImg,binding.profile1Layout,binding.profile2Layout,binding.animationView)
                profileViews.forEach { it.showVisibility(false) }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    private fun initializePlayer() {
        player1 = ExoPlayer.Builder(requireContext()).build()
        player2 = ExoPlayer.Builder(requireContext()).build()
    }

    private fun setupPlayer(){
        prepareExoPlayerFromAssetResource("assets:///user1.mp4",player1)
        prepareExoPlayerFromAssetResource("assets:///user2.mp4",player2)
        startVideo()
    }

    private fun prepareExoPlayerFromAssetResource(uri: String,player: ExoPlayer) {
        val dataSourceFactory = DataSource.Factory { AssetDataSource(requireContext()) }
        val mediaSource = ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(uri)))
        player.addMediaSource(mediaSource)
        player.prepare()
    }


    private fun startVideo(){
        startPlayback()
        player1.volume = 0f
        player2.volume = 0f
        binding.exoPlayerView1.player = player1
        binding.exoPlayerView2.player = player2
    }

    private fun startPlayback() {
        if(this::player1.isInitialized){
            player1.playWhenReady = true
        }
        if(this::player2.isInitialized){
            player2.playWhenReady = true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}