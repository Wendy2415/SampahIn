package com.capstone.sampahin.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.databinding.ActivityLoginBinding
import com.capstone.sampahin.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        observeLoginResult()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(email, password)) {
                loginViewModel.findLogin(email, password)
            }
        }
    }

    private fun observeLoginResult() {
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this,"login success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.etlEmail.error = "Must Fill Email"
            isValid = false
        } else if (!isValidEmail(email)) {
            binding.etlEmail.error = "Invalid Email Format"
            isValid = false
        } else {
            binding.etlEmail.error = null
        }

        if (password.isEmpty()) {
            binding.etlPassword.error = "Must Fill Password"
            isValid = false
        } else if (password.length < 8) {
            binding.etlPassword.error = "Password must be at least 8 characters"
            isValid = false
        } else {
            binding.etlPassword.error = null
        }

        return isValid
    }

    

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        return email.matches(emailPattern.toRegex())
    }

}