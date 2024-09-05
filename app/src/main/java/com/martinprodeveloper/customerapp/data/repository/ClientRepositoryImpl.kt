package com.martinprodeveloper.customerapp.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.martinprodeveloper.customerapp.data.entity.ClientEntity
import com.martinprodeveloper.customerapp.domain.model.ClientModel
import com.martinprodeveloper.customerapp.domain.repository.ClientRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : ClientRepository {

    override suspend fun createClient(client: ClientModel) {
        val clientEntity = ClientEntity.fromDomain(client)
        firebaseDatabase.getReference("clients").child(clientEntity.id ?: "").setValue(clientEntity).await()
    }
}