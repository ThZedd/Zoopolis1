package pt.iade.ei.zoopolis.models

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("rota") val rota: List<Coordinate>
)
