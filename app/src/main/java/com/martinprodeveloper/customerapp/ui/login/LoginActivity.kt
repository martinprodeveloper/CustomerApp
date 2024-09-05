package com.martinprodeveloper.customerapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.martinprodeveloper.customerapp.R
import com.martinprodeveloper.customerapp.databinding.ActivityLoginBinding
import com.martinprodeveloper.customerapp.ui.client.create.ClientCreateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            setUpViews()
            setUpObservers()
        }
    }

    private fun setUpViews() {
        binding.btnVerify.setOnClickListener {
            goToClientCreate()
        }
    }

    private fun setUpObservers() {
        //
    }

    private fun goToClientCreate() {
        val intent = Intent(this, ClientCreateActivity::class.java)
        startActivity(intent)
    }
}