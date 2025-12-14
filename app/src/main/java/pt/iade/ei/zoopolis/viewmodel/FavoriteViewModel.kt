package pt.iade.ei.zoopolis.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pt.iade.ei.zoopolis.models.AnimalDTO
import pt.iade.ei.zoopolis.models.SessionManager
import pt.iade.ei.zoopolis.retrofit.FavoriteRepository
import pt.iade.ei.zoopolis.retrofit.Result

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository,
    context: Context // Adicionando contexto para usar o SessionManager
) : ViewModel() {

    private val sessionManager = SessionManager(context)
    private val personId = sessionManager.getUserId() // Obtendo o ID do usuário dinamicamente

    private val _favoriteAnimals = MutableStateFlow<List<AnimalDTO>>(emptyList())
    val favoriteAnimals = _favoriteAnimals.asStateFlow()

    private val _favoriteStatus = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val favoriteStatus: StateFlow<Map<Int, Boolean>> = _favoriteStatus

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        if (personId != -1) { // Garante que o ID é válido
            loadFavoriteAnimalsByPerson(personId)
        } else {
            Log.e("FavoriteViewModel", "User ID not found in session!")
        }
    }

    private fun loadFavoriteAnimalsByPerson(personId: Int) {
        viewModelScope.launch {
            favoriteRepository.getFavoriteAnimalsByPerson(personId).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> { // CORREÇÃO 1: 'Success' com dois 's'
                        result.data?.let { animals ->
                            _favoriteAnimals.update { animals }

                            // Cria um mapa onde ID -> true para indicar que são favoritos
                            val updatedStatus = animals.associate { it.id to true }
                            _favoriteStatus.update { updatedStatus }

                            Log.d("FavoriteViewModel", "favoriteStatus after load: ${_favoriteStatus.value}")
                        }
                    }
                    is Result.Loading -> {
                        // CORREÇÃO 2: Obrigatório tratar o estado Loading
                        // Podes deixar vazio se não quiseres mostrar nada enquanto carrega
                    }
                }
            }
        }
    }

    fun addFavorite(animalId: Int) {
        if (personId == -1) return
        viewModelScope.launch {
            try {
                val response = favoriteRepository.addFavorite(personId, animalId)
                if (response == "Animal added to favorites successfully.") {
                    _favoriteStatus.update { currentStatus -> currentStatus + (animalId to true) }
                    Log.d("FavoriteViewModel", "favoriteStatus after add: ${_favoriteStatus.value}")
                }
            } catch (e: Exception) {
                _showErrorToastChannel.send(true)
            }
        }
    }

    fun removeFavorite(animalId: Int) {
        if (personId == -1) return
        viewModelScope.launch {
            try {
                val response = favoriteRepository.removeFavorite(personId, animalId)
                if (response == "Animal removed from favorites successfully.") {
                    _favoriteStatus.update { currentStatus -> currentStatus - animalId }
                    Log.d("FavoriteViewModel", "favoriteStatus after remove: ${_favoriteStatus.value}")
                }
            } catch (e: Exception) {
                _showErrorToastChannel.send(true)
            }
        }
    }

    fun checkIfFavorite(animalId: Int) {
        if (personId == -1) return
        viewModelScope.launch {
            try {
                val isFavorite = favoriteRepository.isFavorite(personId, animalId)
                _favoriteStatus.update { it + (animalId to isFavorite) }
                Log.d("FavoriteViewModel", "favoriteStatus after check: ${_favoriteStatus.value}")
            } catch (e: Exception) {
                _showErrorToastChannel.send(true)
            }
        }
    }
}
