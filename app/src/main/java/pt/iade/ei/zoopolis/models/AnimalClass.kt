package pt.iade.ei.zoopolis.models

import java.io.Serializable

data class AnimalClass(
    var id: Int?,
    val className: String,
    val kingdom: String,
    val order: String,
    val family: String,
    val genus: String,
    val specie: String,
):Serializable
