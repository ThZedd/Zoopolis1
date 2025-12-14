package pt.iade.ei.zoopolis.retrofit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.iade.ei.zoopolis.models.EnclosureDTO
import retrofit2.HttpException
import java.io.IOException

class EnclosureDTORepositoryImplementation(private val api: Api) : EnclosureDTORepository {

    override suspend fun getAllEnclosures(): Flow<Result<List<EnclosureDTO>>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getAllEnclosures()
            emit(Result.Success(response))
        } catch (e: IOException) {
            emit(Result.Error(message = "Error fetching enclosures: ${e.message}", exception = e))
        } catch (e: HttpException) {
            emit(Result.Error(message = "HTTP error fetching enclosures: ${e.message()}", exception = e))
        } catch (e: Exception) {
            emit(Result.Error(message = "Unexpected error: ${e.message}", exception = e))
        }
    }

    override suspend fun getEnclosureById(id: Int): Flow<Result<EnclosureDTO>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getEnclosureById(id)
            emit(Result.Success(response))
        } catch (e: IOException) {
            emit(Result.Error(message = "Error fetching enclosure by ID: ${e.message}", exception = e))
        } catch (e: HttpException) {
            emit(Result.Error(message = "HTTP error fetching enclosure by ID: ${e.message()}", exception = e))
        } catch (e: Exception) {
            emit(Result.Error(message = "Unexpected error: ${e.message}", exception = e))
        }
    }
}
