package pt.iade.ei.zoopolis.models

data class Favorite(
    val animals: List<AnimalDTO>,
    val favorite: Boolean,
    val id: Int,
    val person: Person
)