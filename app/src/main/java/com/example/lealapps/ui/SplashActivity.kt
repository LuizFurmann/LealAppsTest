package com.example.lealapps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.lealapps.MainActivity
import com.example.lealapps.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val fullScreemConfig = FullScreemConfig(window)
        fullScreemConfig.hideSystemUIOnCreate()
        fullScreemConfig.hideSystemUIOnWindowFocusChanged(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this@SplashActivity, MainActivity::class.java).also{
                startActivity(it)
            }
            finish()
        }, 3000)
    }

}