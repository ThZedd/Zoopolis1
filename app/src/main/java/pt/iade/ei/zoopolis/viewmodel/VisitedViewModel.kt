package pt.iade.ei.zoopolis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.iade.ei.zoopolis.models.Visited
import pt.iade.ei.zoopolis.retrofit.Result
import pt.iade.ei.zoopolis.retrofit.VisitedRepository

class VisitedViewModel(
    private val visitedRepository: VisitedRepository
) : ViewModel() {

    // LiveData para lista de visitas
    private val _visitsList = MutableLiveData<Result<List<Visited>>>()
    val visitsList: LiveData<Result<List<Visited>>> get() = _visitsList

    // LiveData para uma única visita
    private val _visitDetails = MutableLiveData<Result<Visited>>()
    val visitDetails: LiveData<Result<Visited>> get() = _visitDetails

    // LiveData para resultado de criação de visita
    private val _createVisitResult = MutableLiveData<Result<Visited>>()
    val createVisitResult: LiveData<Result<Visited>> get() = _createVisitResult

    // Função para buscar todas as visitas
    fun getAllVisits() {
        viewModelScope.launch {
            val result = visitedRepository.getAllVisits()
            _visitsList.postValue(result) // Atualiza o LiveData com o resultado
        }
    }

    // Função para buscar detalhes de uma visita por ID
    fun getVisitById(id: Int) {
        viewModelScope.launch {
            val result = visitedRepository.getVisitById(id)
            _visitDetails.postValue(result) // Atualiza o LiveData com o resultado
        }
    }

    // Função para criar uma nova visita
    fun createVisit(personId: Int, animalId: Int) {
        viewModelScope.launch {
            val result = visitedRepository.createVisit(personId, animalId)
            _createVisitResult.postValue(result) // Atualiza o LiveData com o resultado
        }
    }
}
