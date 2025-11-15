package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.AEDTO

interface AEDTORepository {
    // Obter todos os registros AE
    suspend fun getAllAE(): Result<List<AEDTO>>

    // Obter um registro AE pelo ID
    suspend fun getAEById(id: Int): Result<AEDTO>

    suspend fun getAEByAnimalId(animalId: Int): Result<List<AEDTO>>
}
