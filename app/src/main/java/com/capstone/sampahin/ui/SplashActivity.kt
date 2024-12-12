package com.capstone.sampahin.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sampahin.R
import com.capstone.sampahin.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

@Suppress("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = splashScreen
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.iconView?.animate()?.alpha(0f)?.setDuration(0)?.start()
                splashScreenView.remove()
            }
        }

        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startSplashSequence()
    }


    private fun startSplashSequence() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(0)
            typeWriterAnimation(binding.splash1, getString(R.string.splash_title), 150)
            delay(500)
            typeWriterAnimation(binding.splash2, getString(R.string.splash_title2), 150)
            delay(500)
            navigateToMainActivity()
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun typeWriterAnimation(textView: TextView, text: String, delay: Long) {
        textView.visibility = TextView.VISIBLE
        textView.text = ""
        for (char in text) {
            textView.text = textView.text.toString() + char
            delay(delay)
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
