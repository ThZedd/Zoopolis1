package pt.iade.ei.zoopolis

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.collectLatest
import pt.iade.ei.zoopolis.retrofit.AnimalsDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.FavoriteRepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.RetrofitInstance
import pt.iade.ei.zoopolis.ui.menus.AnimalMenuTeste
import pt.iade.ei.zoopolis.ui.theme.ZoopolisTheme
import pt.iade.ei.zoopolis.viewmodel.AnimalDTOViewModel
import pt.iade.ei.zoopolis.viewmodel.FavoriteViewModel

class AnimalMenuActivity : ComponentActivity() {

    private val viewModel by viewModels<AnimalDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AnimalDTOViewModel(AnimalsDTORepositoryImplementation(RetrofitInstance.api))
                        as T
            }
        }
    })
    private val favoriteViewModel by viewModels<FavoriteViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FavoriteViewModel(FavoriteRepositoryImplementation(RetrofitInstance.api),
                    applicationContext)
                 as T
            }
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZoopolisTheme {
                val animalsList = viewModel.animals.collectAsState().value
                val context = LocalContext.current
                val favoriteStatus = favoriteViewModel.favoriteStatus.collectAsState().value
                LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
                    viewModel.showErrorToastChannel.collectLatest { show ->
                        if (show) {
                            Toast.makeText(
                                context, "Erro ao carregar os animais", Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
                if (animalsList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {

                    AnimalMenuTeste()
                }

            }

        }
    }
}



