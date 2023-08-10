package com.ab.clipchat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ab.clipchat.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import rx.android.schedulers.AndroidSchedulers


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
        binding.appBarLayout.toolbar.elevation = 0f
        bind()
    }

    private fun bind(){
        ClipChatApplication.getNetworkSubject()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnected ->
                Toast.makeText(this,getString(R.string.device_offline),Toast.LENGTH_SHORT).show()
            }
    }
}

