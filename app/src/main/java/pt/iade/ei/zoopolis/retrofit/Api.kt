package pt.iade.ei.zoopolis.retrofit

import pt.iade.ei.zoopolis.models.AEDTO
import pt.iade.ei.zoopolis.models.AnimalDTO
import pt.iade.ei.zoopolis.models.EnclosureDTO
import pt.iade.ei.zoopolis.models.LoginRequestDTO
import pt.iade.ei.zoopolis.models.LoginResponseDTO
import pt.iade.ei.zoopolis.models.Person
import pt.iade.ei.zoopolis.models.Visited
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("animalsDTO")
    suspend fun getAnimals(): List<AnimalDTO>

    @GET("animalsDTO/{id}")
    suspend fun getAnimalsById(@Path("id") id: Int): AnimalDTO

    @GET("persons")
    suspend fun getPersons(): List<Person>

    @GET("persons/{id}")
    suspend fun getPersonsById(@Path("id") id: Int): Person

    @GET("favorite/person/{personId}")
    suspend fun getFavoriteAnimalsByPerson(@Path("personId") personId: Int): List<AnimalDTO>

    // Verificar se um animal é favorito de uma pessoa
    @GET("favorite/isFavorite")
    suspend fun isFavorite(
        @Query("personId") personId: Int,
        @Query("animalId") animalId: Int
    ): Boolean

    // Adicionar um animal aos favoritos de uma pessoa
    @POST("favorite/add")
    suspend fun addFavorite(
        @Query("personId") personId: Int,
        @Query("animalId") animalId: Int
    ): String

    // Remover um animal dos favoritos de uma pessoa
    @DELETE("favorite/remove")
    suspend fun removeFavorite(
        @Query("personId") personId: Int,
        @Query("animalId") animalId: Int
    ): String

    @POST("persons/login")
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): LoginResponseDTO


    @POST("persons/register")
    suspend fun register(@Body person: Person): Person

    @POST("persons/{id}/add-point")
    suspend fun addPointToPerson(@Path("id") id: Int): String

    // Obter todos os registros AE
    @GET("ae")
    suspend fun getAllAE(): List<AEDTO>

    // Obter AE por ID
    @GET("ae/{id}")
    suspend fun getAEById(@Path("id") id: Int): AEDTO

    // Obter registros AE vinculados a um animal específico
    @GET("ae/animal/{animalId}")
    suspend fun getAEByAnimalId(@Path("animalId") animalId: Int): List<AEDTO>

    // Criar uma nova visita
    @POST("visited")
    suspend fun createVisit(
        @Query("personId") personId: Int,
        @Query("animalId") animalId: Int
    ): Visited

    // Obter todas as visitas
    @GET("visited")
    suspend fun getAllVisits(): List<Visited>

    // Obter uma visita pelo ID
    @GET("visited/{id}")
    suspend fun getVisitById(@Path("id") id: Int): Visited

    @GET("enclosuresDTO")
    suspend fun getAllEnclosures(): List<EnclosureDTO>

    @GET("enclosuresDTO/{id}")
    suspend fun getEnclosureById(@Path("id") id: Int): EnclosureDTO



    companion object{

        const val BASE_URL = "http://10.0.2.2:8081/api/"

    }
}