package pt.iade.ei.zoopolis.retrofit

import kotlinx.coroutines.flow.Flow
import pt.iade.ei.zoopolis.models.AnimalDTO

interface AnimalsDTORepository {
    suspend fun getAnimals(): Flow<Result<List<AnimalDTO>>>

    suspend fun getAnimalsById(id: Int): Flow<Result<AnimalDTO>>

}