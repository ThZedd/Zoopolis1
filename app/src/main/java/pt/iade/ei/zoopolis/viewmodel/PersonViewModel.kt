package pt.iade.ei.zoopolis.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.iade.ei.zoopolis.models.LoginRequestDTO
import pt.iade.ei.zoopolis.models.LoginResponseDTO
import pt.iade.ei.zoopolis.models.Person
import pt.iade.ei.zoopolis.models.SessionManager
import pt.iade.ei.zoopolis.retrofit.PersonRepository
import pt.iade.ei.zoopolis.retrofit.Result

class PersonViewModel(
    private val personRepository: PersonRepository,
    context: Context // Adicionando contexto para o SessionManager
) : ViewModel() {

    private val sessionManager = SessionManager(context) // Inicializando o SessionManager

    // LiveData para resultados de login
    private val _loginResult = MutableLiveData<Result<LoginResponseDTO>?>()
    val loginResult: MutableLiveData<Result<LoginResponseDTO>?> get() = _loginResult

    // LiveData para resultados de registro
    private val _registerResult = MutableLiveData<Result<Person>>()
    val registerResult: LiveData<Result<Person>> get() = _registerResult

    // LiveData para lista de pessoas
    private val _personsList = MutableLiveData<Result<List<Person>>>()
    val personsList: LiveData<Result<List<Person>>> get() = _personsList

    // LiveData para resultado de adicionar pontos
    private val _addPointResult = MutableLiveData<Result<String>>()
    val addPointResult: LiveData<Result<String>> get() = _addPointResult

    // Função para fazer login
    fun login(loginRequestDTO: LoginRequestDTO) {
        viewModelScope.launch {
            val result = personRepository.login(loginRequestDTO)
            _loginResult.postValue(result) // Atualiza o LiveData com o resultado

            if (result is Result.Sucess) {
                val loginResponse = result.data
                if (loginResponse != null) {
                    // Salvar token e user_id na sessão
                    sessionManager.saveSession(
                        token = loginResponse.token,
                        userId = loginResponse.personId
                    )
                }
            }
        }
    }

    // Função para registrar pessoa
    fun register(person: Person) {
        viewModelScope.launch {
            val result = personRepository.register(person)
            _registerResult.postValue(result) // Atualiza o LiveData com o resultado
        }
    }

    // Função para pegar a lista de pessoas
    fun getPersons() {
        viewModelScope.launch {
            val result = personRepository.getPersons()
            _personsList.postValue(result) // Atualiza o LiveData com o resultado
        }
    }

    fun logout() {
        sessionManager.clearSession()
    }

    fun getPersonById(personId: Int): LiveData<Result<Person>> {
        val personLiveData = MutableLiveData<Result<Person>>()
        viewModelScope.launch {
            val result = personRepository.getPersonById(personId)
            personLiveData.postValue(result)
        }
        return personLiveData
    }

    // Função para adicionar um ponto ao usuário
    fun addPointToPerson(personId: Int) {
        viewModelScope.launch {
            val result = personRepository.addPointToPerson(personId)
            _addPointResult.postValue(result) // Atualiza o LiveData com o resultado
        }
    }

}
