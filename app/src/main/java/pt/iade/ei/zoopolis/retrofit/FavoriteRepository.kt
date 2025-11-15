package pt.iade.ei.zoopolis.retrofit

import kotlinx.coroutines.flow.Flow
import pt.iade.ei.zoopolis.models.AnimalDTO

interface FavoriteRepository {

    suspend fun getFavoriteAnimalsByPerson(personId: Int): Flow<Result<List<AnimalDTO>>>

    suspend fun isFavorite(personId: Int, animalId: Int): Boolean

    suspend fun addFavorite(personId: Int, animalId: Int): String

    suspend fun removeFavorite(personId: Int, animalId: Int): String
}