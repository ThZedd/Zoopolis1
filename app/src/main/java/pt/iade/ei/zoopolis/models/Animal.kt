package pt.iade.ei.zoopolis.models

import java.io.Serializable

data class Animal(
    var id: Int?,
    val name: String,
    var imageRes: Int,
    val description: String,
    val weight: Float,
    val height: Float,
    val classs: List<AnimalClass>
): Serializable
