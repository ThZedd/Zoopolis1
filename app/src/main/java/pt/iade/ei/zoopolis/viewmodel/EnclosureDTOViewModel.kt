package pt.iade.ei.zoopolis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pt.iade.ei.zoopolis.models.EnclosureDTO
import pt.iade.ei.zoopolis.retrofit.EnclosureDTORepository
import pt.iade.ei.zoopolis.retrofit.Result

class EnclosureDTOViewModel(
    private val enclosureRepository: EnclosureDTORepository
) : ViewModel() {

    // StateFlow para lista de EnclosureDTO
    private val _enclosureList = MutableStateFlow<Result<List<EnclosureDTO>>>(Result.Error(message = "Loading..."))
    val enclosureList: StateFlow<Result<List<EnclosureDTO>>> get() = _enclosureList

    // LiveData para EnclosureDTO por ID
    private val _enclosureById = MutableLiveData<Result<EnclosureDTO>>()
    val enclosureById: LiveData<Result<EnclosureDTO>> get() = _enclosureById

    // Função para buscar todos os EnclosureDTO
    fun getAllEnclosures() {
        viewModelScope.launch {
            enclosureRepository.getAllEnclosures().collect { result ->
                _enclosureList.value = result // Usa a atribuição correta para StateFlow
            }
        }
    }

    // Função para buscar EnclosureDTO por ID
    fun loadEnclosureById(id: Int) {
        viewModelScope.launch {
            enclosureRepository.getEnclosureById(id).collect { result ->
                _enclosureById.postValue(result) // Atualiza o LiveData com o resultado
            }
        }
    }
}
