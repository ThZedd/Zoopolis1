package pt.iade.ei.zoopolis.models

import java.io.Serializable

data class AnimalDTO(
    val ciName: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val name: String
):Serializable