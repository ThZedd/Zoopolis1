package pt.iade.ei.zoopolis.models

import java.io.Serializable

data class LoginResponseDTO(
    val personId: Int,
    val token: String
): Serializable