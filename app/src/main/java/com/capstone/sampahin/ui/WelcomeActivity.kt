package com.capstone.sampahin.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.capstone.sampahin.data.TokenPreferences
import com.capstone.sampahin.databinding.ActivityWelcomeBinding
import com.capstone.sampahin.ui.login.LoginActivity
import com.capstone.sampahin.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeBinding
    private lateinit var tokenPref : TokenPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenPref = TokenPreferences(this)

        lifecycleScope.launch {
            if (!tokenPref.getToken().isNullOrEmpty()) {
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.registerbtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginbtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        playAnimation()

    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.SCALE_X, 0.9F, 1.1F).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvWelcome = ObjectAnimator.ofFloat(binding.tvWelcome,View.ALPHA, 1F).setDuration(1000)
        val tvDesc = ObjectAnimator.ofFloat(binding.tvDesc,View.ALPHA, 1F).setDuration(1000)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginbtn,View.ALPHA, 1F).setDuration(1000)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerbtn,View.ALPHA, 1F).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(btnLogin, btnRegister)
        }
        AnimatorSet().apply {
            playSequentially(tvWelcome, tvDesc, together)
            start()
        }
    }
}