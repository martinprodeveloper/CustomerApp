package com.martinprodeveloper.customerapp.ui.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import java.util.concurrent.TimeUnit
import com.google.firebase.FirebaseException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val phoneAuthProvider: PhoneAuthProvider
) : ViewModel() {

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: LiveData<AuthenticationState> get() = _authenticationState

    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // Verificación automática completada, autenticar al usuario
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _authenticationState.value = AuthenticationState.Error(e.message)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // Guardar el ID de verificación
                _authenticationState.value = AuthenticationState.CodeSent(verificationId)
            }
        }

        phoneAuthProvider.verifyPhoneNumber(
            phoneNumber,                  // Número de teléfono a verificar
            60,                           // Tiempo de espera en segundos
            TimeUnit.SECONDS,             // Unidad de tiempo
            activity,                     // Actividad actual
            callbacks                     // Callbacks para el estado de la verificación
        )
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authenticationState.value = AuthenticationState.Authenticated
                } else {
                    _authenticationState.value = AuthenticationState.Error(task.exception?.message)
                }
            }
    }

    sealed class AuthenticationState {
        object Authenticated : AuthenticationState()
        data class CodeSent(val verificationId: String) : AuthenticationState()
        data class Error(val message: String?) : AuthenticationState()
    }
}