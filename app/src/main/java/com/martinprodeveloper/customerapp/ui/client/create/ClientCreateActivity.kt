package com.martinprodeveloper.customerapp.ui.client.create

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.martinprodeveloper.customerapp.R
import com.martinprodeveloper.customerapp.databinding.ActivityClientCreateBinding
import com.martinprodeveloper.customerapp.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ClientCreateActivity : AppCompatActivity() {

    private val TAG = "ClientCreateActivity"
    private lateinit var binding: ActivityClientCreateBinding
    private val calendar: Calendar = Calendar.getInstance()
    private val clientCreateViewModel: ClientCreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.actionBar.toolbar
        val titleColor = ContextCompat.getColor(this, R.color.md_theme_primary)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_client_create)

        toolbar.setTitleTextColor(titleColor)

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
        binding.etBirthDate.setOnClickListener {
            showDatePicker()
        }
        binding.btnSend.setOnClickListener {
            saveClient()
        }
    }

    private fun setUpObservers() {
        clientCreateViewModel.clientCreationState.observe(this) { state ->
            when (state) {
                is ClientCreateViewModel.ClientCreationState.Success -> {
                    Toast.makeText(this, "Cliente creado con éxito", Toast.LENGTH_LONG).show()
                    goToHome()
                }
                is ClientCreateViewModel.ClientCreationState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.etBirthDate.setText(sdf.format(calendar.time))
    }

    private fun saveClient() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val age = binding.etAge.text.toString().trim()
        val birthDate = binding.etBirthDate.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || birthDate.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
            return
        }

        clientCreateViewModel.createClient(
            firstName = firstName,
            lastName = lastName,
            age = age.toInt(),
            birthDate = birthDate
        )
    }

    private fun goToHome() {
        Log.d(TAG, "Navegando a HomeActivity")
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}