package com.example.lealappstest.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivitySplashBinding
import com.example.lealappstest.view.authentication.LoginActivity

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this@SplashActivity, LoginActivity::class.java).also{
                startActivity(it)
            }
            finish()
        }, 2000)
    }
}