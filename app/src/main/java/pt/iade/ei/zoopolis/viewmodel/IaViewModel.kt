package pt.iade.ei.zoopolis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.iade.ei.zoopolis.models.RouteRequest
import pt.iade.ei.zoopolis.models.RouteResponse
import pt.iade.ei.zoopolis.retrofit.IaRepository
import pt.iade.ei.zoopolis.retrofit.Result

class IaViewModel(private val repository: IaRepository) : ViewModel() {

    private val _route = MutableStateFlow<Result<RouteResponse>?>(null)
    val route = _route.asStateFlow()

    fun getRoute(routeRequest: RouteRequest) {
        viewModelScope.launch {
            val result = repository.getRoute(routeRequest)
            _route.value = result
        }
    }
}