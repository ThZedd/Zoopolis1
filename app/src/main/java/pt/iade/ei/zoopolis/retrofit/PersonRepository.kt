package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.LoginRequestDTO
import pt.iade.ei.zoopolis.models.LoginResponseDTO
import pt.iade.ei.zoopolis.models.Person

interface PersonRepository {
    // Obter lista de pessoas
    suspend fun getPersons(): Result<List<Person>>

    // Obter uma pessoa pelo ID
    suspend fun getPersonById(id: Int): Result<Person>

    // Fazer login
    suspend fun login(loginRequestDTO: LoginRequestDTO): Result<LoginResponseDTO>?

    // Registrar uma nova pessoa
    suspend fun register(person: Person): Result<Person>

    // Adicionar 1 ponto aos pontos de um usuário
    suspend fun addPointToPerson(id: Int): Result<String>

}
