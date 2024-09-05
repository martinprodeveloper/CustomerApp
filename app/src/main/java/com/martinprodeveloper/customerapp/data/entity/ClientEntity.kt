package com.martinprodeveloper.customerapp.data.entity

import com.martinprodeveloper.customerapp.domain.model.ClientModel

data class ClientEntity(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null,
    val birthDate: String? = null
) {
    fun toDomain(): ClientModel {
        return ClientModel(
            id = id ?: "",
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            age = age ?: 0,
            birthDate = birthDate ?: ""
        )
    }

    companion object {
        fun fromDomain(client: ClientModel): ClientEntity {
            return ClientEntity(
                id = client.id,
                firstName = client.firstName,
                lastName = client.lastName,
                age = client.age,
                birthDate = client.birthDate
            )
        }
    }
}
