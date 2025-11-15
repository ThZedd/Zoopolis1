package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.Visited

interface VisitedRepository {

    // Obter todas as visitas
    suspend fun getAllVisits(): Result<List<Visited>>

    // Obter uma visita pelo ID
    suspend fun getVisitById(id: Int): Result<Visited>

    // Criar uma nova visita
    suspend fun createVisit(personId: Int, animalId: Int): Result<Visited>
}
