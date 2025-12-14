package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.RouteRequest
import pt.iade.ei.zoopolis.models.RouteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IaApi {
    @POST("rotas/calcular")
    suspend fun getRoute(@Body routeRequest: RouteRequest): Response<RouteResponse>
}