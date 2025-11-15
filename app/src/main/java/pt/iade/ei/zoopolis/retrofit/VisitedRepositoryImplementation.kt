package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.Visited

class VisitedRepositoryImplementation(private val api: Api) : VisitedRepository {

    override suspend fun getAllVisits(): Result<List<Visited>> {
        return try {
            val visits = api.getAllVisits()
            Result.Sucess(visits)
        } catch (e: Exception) {
            Result.Error(message = "Failed to fetch visits: ${e.message}")
        }
    }

    override suspend fun getVisitById(id: Int): Result<Visited> {
        return try {
            val visit = api.getVisitById(id)
            Result.Sucess(visit)
        } catch (e: Exception) {
            Result.Error(message = "Failed to fetch visit with ID $id: ${e.message}")
        }
    }

    override suspend fun createVisit(personId: Int, animalId: Int): Result<Visited> {
        return try {
            val visit = api.createVisit(personId, animalId)
            Result.Sucess(visit)
        } catch (e: Exception) {
            Result.Error(message = "Failed to create visit: ${e.message}")
        }
    }
}
