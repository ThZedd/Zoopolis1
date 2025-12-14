package pt.iade.ei.zoopolis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pt.iade.ei.zoopolis.models.AnimalDTO
import pt.iade.ei.zoopolis.retrofit.AnimalsDTORepository
import pt.iade.ei.zoopolis.retrofit.Result

class AnimalDTOViewModel(
    private val animalsrepository: AnimalsDTORepository
):ViewModel() {

    private val _animals = MutableStateFlow<List<AnimalDTO>>(emptyList())
    val animals = _animals.asStateFlow()

    private val _animalById = MutableStateFlow<AnimalDTO?>(null)
    val animalById = _animalById.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        loadAnimals()
    }
    private fun loadAnimals() {
        viewModelScope.launch {
            animalsrepository.getAnimals().collect { result ->
                when (result) {
                    is Result.Error -> {
                        // Se quiseres ver o erro no Logcat, podes usar:
                        // Log.e("AnimalViewModel", "Erro: ${result.message}", result.exception)
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> { // CORREÇÃO: Escrito com dois 's'
                        result.data?.let { animals ->
                            _animals.update {
                                animals
                            }
                        }
                    }
                    is Result.Loading -> {
                        // CORREÇÃO: Obrigatório tratar o estado Loading
                        // Podes usar isto para mostrar uma barra de progresso se tiveres uma variável _isLoading
                    }
                }
            }
        }
    }

    fun loadAnimalById(id: Int) {
        viewModelScope.launch {
            animalsrepository.getAnimalsById(id).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> { // CORREÇÃO: Escrito com dois 's'
                        result.data?.let { animal ->
                            _animalById.update {
                                animal
                            }
                        }
                    }
                    is Result.Loading -> {
                        // CORREÇÃO: Obrigatório tratar o estado Loading
                    }
                }
            }
        }
    }
}