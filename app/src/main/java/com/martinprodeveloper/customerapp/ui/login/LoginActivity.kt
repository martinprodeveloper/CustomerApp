package com.martinprodeveloper.customerapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.martinprodeveloper.customerapp.R
import com.martinprodeveloper.customerapp.databinding.ActivityLoginBinding
import com.martinprodeveloper.customerapp.ui.login.verification.LoginVerificationActivity
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
            val phoneNumber = binding.etPhoneNumber.text.toString()
            loginViewModel.sendVerificationCode(phoneNumber, this)  // Pasar la instancia de la actividad
        }
    }

    private fun setUpObservers() {
        loginViewModel.authenticationState.observe(this) { state ->
            when (state) {
                is LoginViewModel.AuthenticationState.Authenticated -> {
                    Log.d(TAG, "Estado: Autenticado")
                }
                is LoginViewModel.AuthenticationState.CodeSent -> {
                    Log.d(TAG, "Código enviado: ${state.verificationId}")
                    Toast.makeText(this, "Código enviado", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginVerificationActivity::class.java)
                    intent.putExtra("verificationId", state.verificationId)
                    startActivity(intent)
                }
                is LoginViewModel.AuthenticationState.Error -> {
                    Log.e(TAG, "Error: ${state.message}")
                    Toast.makeText(this, "Error al autenticarse", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}