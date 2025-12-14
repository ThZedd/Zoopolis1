package pt.iade.ei.zoopolis.models

import com.google.gson.annotations.SerializedName

data class RouteRequest(
    @SerializedName("paragens") val paragens: List<Coordinate>
)

data class Coordinate(val lat: Double, val lng: Double)
