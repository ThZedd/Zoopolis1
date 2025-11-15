package pt.iade.ei.zoopolis.models

import java.io.Serializable

data class Person(
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val gender: Char,
    val points: Int
) : Serializable
