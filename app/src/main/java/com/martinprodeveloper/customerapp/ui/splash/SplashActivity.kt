package com.martinprodeveloper.customerapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.martinprodeveloper.customerapp.BuildConfig
import com.martinprodeveloper.customerapp.R
import com.martinprodeveloper.customerapp.databinding.ActivitySplashBinding
import com.martinprodeveloper.customerapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            setUpViews()
        }

    }

    private fun setUpViews() {
        val text = getString(R.string.app_environment)
        val environmentName = BuildConfig.FLAVOR
        val animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)

        binding.tvEnvironment.text = "$text $environmentName"
        binding.ivSplash.startAnimation(animation)
    }

    private fun startSplashScreenHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            goToHome()
        }, 2500.toLong())
    }

    private fun goToHome() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        startSplashScreenHandler()
    }
}