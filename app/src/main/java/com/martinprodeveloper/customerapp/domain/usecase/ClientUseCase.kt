package com.martinprodeveloper.customerapp.domain.usecase

import com.martinprodeveloper.customerapp.domain.model.ClientModel
import com.martinprodeveloper.customerapp.domain.repository.ClientRepository
import javax.inject.Inject

class ClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository
) {
    suspend fun createClient(client: ClientModel) {
        clientRepository.createClient(client)
    }
}