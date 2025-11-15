package pt.iade.ei.zoopolis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.iade.ei.zoopolis.models.AEDTO
import pt.iade.ei.zoopolis.retrofit.AEDTORepository
import pt.iade.ei.zoopolis.retrofit.Result

class AEDTOViewModel(
    private val aeRepository: AEDTORepository
) : ViewModel() {

    // StateFlow para lista de AEDTO
    private val _aeList = MutableStateFlow<Result<List<AEDTO>>>(Result.Error(message = "Loading..."))
    val aeList: StateFlow<Result<List<AEDTO>>> get() = _aeList

    // LiveData para um único AEDTO pelo ID
    private val _aeDetail = MutableLiveData<Result<AEDTO>>()
    val aeDetail: LiveData<Result<AEDTO>> get() = _aeDetail

    private val _aeByAnimalId = MutableLiveData<Result<List<AEDTO>>>()
    val aeByAnimalId: LiveData<Result<List<AEDTO>>> get() = _aeByAnimalId

    // Função para buscar todos os AE
    fun getAllAE() {
        viewModelScope.launch {
            val result = aeRepository.getAllAE()
            _aeList.value = result // Atualiza o StateFlow com o resultado
        }
    }

    // Função para buscar AE por ID
    fun getAEById(aeId: Int) {
        viewModelScope.launch {
            val result = aeRepository.getAEById(aeId)
            // Atualiza o StateFlow com o resultado
        }
    }


    // Função para buscar AE por animalId
    fun getAEByAnimalId(animalId: Int) {
        viewModelScope.launch {
            val result = aeRepository.getAEByAnimalId(animalId)
            _aeByAnimalId.postValue(result)
        }
    }
}
