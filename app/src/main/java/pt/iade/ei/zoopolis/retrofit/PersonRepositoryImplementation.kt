package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.LoginRequestDTO
import pt.iade.ei.zoopolis.models.LoginResponseDTO
import pt.iade.ei.zoopolis.models.Person
import retrofit2.HttpException
import java.io.IOException

class PersonRepositoryImplementation(private val api: Api) : PersonRepository {

    override suspend fun getPersons(): Result<List<Person>> {
        return try {
            val response = api.getPersons()
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(message = "Error fetching persons: ${e.message}", exception = e)
        } catch (e: HttpException) {
            Result.Error(message = "HTTP error fetching persons: ${e.message()}", exception = e)
        } catch (e: Exception) {
            Result.Error(message = "Unexpected error: ${e.message}", exception = e)
        }
    }

    override suspend fun getPersonById(id: Int): Result<Person> {
        return try {
            val response = api.getPersonsById(id)
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(message = "Error fetching person by ID: ${e.message}", exception = e)
        } catch (e: HttpException) {
            Result.Error(message = "HTTP error fetching person by ID: ${e.message()}", exception = e)
        } catch (e: Exception) {
            Result.Error(message = "Unexpected error: ${e.message}", exception = e)
        }
    }

    override suspend fun login(loginRequestDTO: LoginRequestDTO): Result<LoginResponseDTO> {
        return try {
            val response = api.login(loginRequestDTO)
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(message = "Login failed: ${e.message}", exception = e)
        } catch (e: HttpException) {
            Result.Error(message = "Email or password incorrect ${e.message()}", exception = e)
        } catch (e: Exception) {
            Result.Error(message = "Unexpected error during login: ${e.message}", exception = e)
        }
    }

    override suspend fun register(person: Person): Result<Person> {
        return try {
            val response = api.register(person)
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(message = "Registration failed: ${e.message}", exception = e)
        } catch (e: HttpException) {
            Result.Error(message = "You need to fill all the fields ${e.message()}", exception = e)
        } catch (e: Exception) {
            Result.Error(message = "Unexpected error during registration: ${e.message}", exception = e)
        }
    }

    override suspend fun addPointToPerson(id: Int): Result<String> {
        return try {
            val response = api.addPointToPerson(id)
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(message = "Error adding point to person: ${e.message}", exception = e)
        } catch (e: HttpException) {
            Result.Error(message = "HTTP error adding point to person: ${e.message()}", exception = e)
        } catch (e: Exception) {
            Result.Error(message = "Unexpected error adding point: ${e.message}", exception = e)
        }
    }

}
