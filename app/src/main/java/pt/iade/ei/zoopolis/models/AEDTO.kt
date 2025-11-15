package pt.iade.ei.zoopolis.models

data class AEDTO(
    val animal: AnimalDTO,
    val code: String,
    val dateIn: String,
    val dateOut: String,
    val enclosure: EnclosureDTO,
    val id: Int
)