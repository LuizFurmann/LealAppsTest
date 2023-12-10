package com.example.lealapps.ui

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat

class FullScreemConfig (private val window: Window){

    fun hideSystemUIOnCreate() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            @Suppress("DEPRECATION")
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        } else {
            window?.let {
                WindowCompat.setDecorFitsSystemWindows(it, false)
            }
        }
    }

    fun hideSystemUIOnWindowFocusChanged(view: View) {
        //view is rootView (ex: binding.root)
        val window = window ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Tell the window that we want to handle/fit any system windows
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val controller = view.windowInsetsController
            // Hide the keyboard (IME)
            controller?.hide(WindowInsets.Type.ime())
            // Sticky Immersive is now ...
            controller?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // When we want to hide the system bars
            controller?.hide(WindowInsets.Type.systemBars())
        } else {
            //noinspection
            @Suppress("DEPRECATION")
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}