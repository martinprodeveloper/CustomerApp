package com.martinprodeveloper.customerapp.ui.login.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVerificationViewModel @Inject constructor() : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _verificationState = MutableLiveData<VerificationState>()
    val verificationState: LiveData<VerificationState> get() = _verificationState

    fun verifyCode(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _verificationState.value = VerificationState.Authenticated
                } else {
                    _verificationState.value = VerificationState.Error(task.exception?.message)
                }
            }
    }

    sealed class VerificationState {
        object Authenticated : VerificationState()
        data class Error(val message: String?) : VerificationState()
    }
}