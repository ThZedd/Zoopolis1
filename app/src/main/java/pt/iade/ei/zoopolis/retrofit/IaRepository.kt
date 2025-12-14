package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.RouteRequest
import pt.iade.ei.zoopolis.models.RouteResponse

interface IaRepository {
    suspend fun getRoute(routeRequest: RouteRequest): Result<RouteResponse>
}

class IaRepositoryImplementation(private val api: IaApi) : IaRepository {
    override suspend fun getRoute(routeRequest: RouteRequest): Result<RouteResponse> {
        return try {
            val response = api.getRoute(routeRequest)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Response was not successful")
            }
        } catch (e: Exception) {
            Result.Error("An unexpected error occurred", e)
        }
    }
}