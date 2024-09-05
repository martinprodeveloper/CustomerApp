package com.martinprodeveloper.customerapp.domain.repository

import com.martinprodeveloper.customerapp.domain.model.ClientModel

interface ClientRepository {
    suspend fun createClient(client: ClientModel)
}