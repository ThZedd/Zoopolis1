package pt.iade.ei.zoopolis.retrofit

import kotlinx.coroutines.flow.Flow
import pt.iade.ei.zoopolis.models.EnclosureDTO

interface EnclosureDTORepository {

    // Retrieves all enclosures as a Flow of Result containing a list of EnclosureDTO
    suspend fun getAllEnclosures(): Flow<Result<List<EnclosureDTO>>>

    // Retrieves a specific enclosure by its ID as a Flow of Result containing an EnclosureDTO
    suspend fun getEnclosureById(id: Int): Flow<Result<EnclosureDTO>>
}
