package pt.iade.ei.zoopolis.models

data class Visited(
    val dtime: String,
    val id: Int,
    val person: Person,
    val subArea: SubArea
)