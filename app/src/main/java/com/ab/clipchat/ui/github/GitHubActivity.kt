package com.ab.clipchat.ui.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ab.clipchat.databinding.UserViewBinding

class GitHubActivity : AppCompatActivity() {

    private lateinit var binding: UserViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val htmlUrl = intent.getStringExtra("html_url")!!
        binding.webView.loadUrl(htmlUrl)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}
