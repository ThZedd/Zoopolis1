package pt.iade.ei.zoopolis.retrofit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import pt.iade.ei.zoopolis.models.AnimalDTO
import retrofit2.HttpException


class AnimalsDTORepositoryImplementation(
    private val api: Api
): AnimalsDTORepository  {
    override suspend fun getAnimals(): Flow<Result<List<AnimalDTO>>> {
        return flow {
            val animalsFromApi = try {
                api.getAnimals()
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
            emit(Result.Sucess(animalsFromApi))
        }

    }
    override suspend fun getAnimalsById(id: Int): Flow<Result<AnimalDTO>> {
        return flow {
            val animalsByIdFromApi = try {
                api.getAnimalsById(id)

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
            emit(Result.Sucess(animalsByIdFromApi))
        }
    }
}