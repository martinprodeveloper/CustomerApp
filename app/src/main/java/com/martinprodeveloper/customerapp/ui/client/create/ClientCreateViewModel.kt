package com.martinprodeveloper.customerapp.ui.client.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinprodeveloper.customerapp.domain.model.ClientModel
import com.martinprodeveloper.customerapp.domain.usecase.ClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ClientCreateViewModel @Inject constructor(
    private val clientUseCase: ClientUseCase
) : ViewModel() {

    private val _clientCreationState = MutableLiveData<ClientCreationState>()
    val clientCreationState: LiveData<ClientCreationState> get() = _clientCreationState

    fun createClient(firstName: String, lastName: String, age: Int, birthDate: String) {
        viewModelScope.launch {
            try {
                val client = ClientModel(
                    id = UUID.randomUUID().toString(),
                    firstName = firstName,
                    lastName = lastName,
                    age = age,
                    birthDate = birthDate
                )
                clientUseCase.createClient(client)
                _clientCreationState.value = ClientCreationState.Success
            } catch (e: Exception) {
                _clientCreationState.value = ClientCreationState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    sealed class ClientCreationState {
        object Success : ClientCreationState()
        data class Error(val message: String) : ClientCreationState()
    }
}