package com.martinprodeveloper.customerapp.ui.login.verification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.martinprodeveloper.customerapp.R
import com.martinprodeveloper.customerapp.databinding.ActivityLoginVerificationBinding
import com.martinprodeveloper.customerapp.ui.client.create.ClientCreateActivity
import com.martinprodeveloper.customerapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginVerificationActivity : AppCompatActivity() {

    private val TAG = "LoginVerificationActivity"
    private lateinit var binding: ActivityLoginVerificationBinding
    private val verificationViewModel: LoginVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginVerificationBinding.inflate(layoutInflater)
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
        val verificationId = intent.getStringExtra("verificationId") ?: return

        binding.btnVerifyCode.setOnClickListener {
            val code = binding.etVerificationCode.text.toString()
            verificationViewModel.verifyCode(verificationId, code)
        }
    }

    private fun setUpObservers() {
        verificationViewModel.verificationState.observe(this) { state ->
            when (state) {
                is LoginVerificationViewModel.VerificationState.Authenticated -> {
                    Log.d(TAG, "Código verificado, usuario autenticado")
                    goToClientCreate()
                }
                is LoginVerificationViewModel.VerificationState.Error -> {
                    Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToClientCreate() {
        Log.d(TAG, "Navegando a ClientCreateActivity")
        val intent = Intent(this, ClientCreateActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}