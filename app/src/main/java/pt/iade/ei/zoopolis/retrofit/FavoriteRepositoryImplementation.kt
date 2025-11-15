package pt.iade.ei.zoopolis.retrofit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import pt.iade.ei.zoopolis.models.AnimalDTO
import retrofit2.HttpException


class FavoriteRepositoryImplementation(
    private val api: Api
): FavoriteRepository  {

    override suspend fun getFavoriteAnimalsByPerson(personId: Int): Flow<Result<List<AnimalDTO>>> {
        return flow {
            val favoriteAnimalsByPersonFromApi = try {
                api.getFavoriteAnimalsByPerson(personId)

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Erro ao carregar os animais"))
                return@flow

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Erro ao carregar os animais"))
                return@flow

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Erro ao carregar os animais"))
                return@flow
            }
            emit(Result.Sucess(favoriteAnimalsByPersonFromApi))
        }
    }

    override suspend fun isFavorite(personId: Int, animalId: Int): Boolean {
        return api.isFavorite(personId, animalId)
    }

    override suspend fun addFavorite(personId: Int, animalId: Int): String {
        return api.addFavorite(personId, animalId)
    }

    override suspend fun removeFavorite(personId: Int, animalId: Int): String {
        return api.removeFavorite(personId, animalId)
    }
}