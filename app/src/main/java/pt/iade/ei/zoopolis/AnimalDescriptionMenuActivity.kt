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
import pt.iade.ei.zoopolis.retrofit.AEDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.AnimalsDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.PersonRepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.RetrofitInstance
import pt.iade.ei.zoopolis.retrofit.VisitedRepositoryImplementation
import pt.iade.ei.zoopolis.ui.menus.AnimalDescriptionMenuTeste
import pt.iade.ei.zoopolis.ui.theme.ZoopolisTheme
import pt.iade.ei.zoopolis.viewmodel.AEDTOViewModel
import pt.iade.ei.zoopolis.viewmodel.AnimalDTOViewModel
import pt.iade.ei.zoopolis.viewmodel.PersonViewModel
import pt.iade.ei.zoopolis.viewmodel.VisitedViewModel

class AnimalDescriptionMenuActivity : ComponentActivity() {
    private val viewModel by viewModels<AnimalDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AnimalDTOViewModel(AnimalsDTORepositoryImplementation(RetrofitInstance.api))
                        as T
            }
        }
    })
    private val AEDTOViewModel by viewModels<AEDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AEDTOViewModel(
                    AEDTORepositoryImplementation(RetrofitInstance.api))
                        as T
            }
        }
    })

    private val VisitedViewModel by viewModels<VisitedViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VisitedViewModel(
                    VisitedRepositoryImplementation(RetrofitInstance.api)
                )
                        as T
            }
        }
    })

    private val PersonViewModel by viewModels<PersonViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PersonViewModel(
                    PersonRepositoryImplementation(RetrofitInstance.api),
                    applicationContext)
                        as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val animalId = intent.getIntExtra("animal_id", -1) // Obtém o ID do animal
        if (animalId == -1) {
            Toast.makeText(this, "Erro: Animal não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.loadAnimalById(animalId)

        setContent {

            ZoopolisTheme {
                val animal = viewModel.animalById.collectAsState().value
                val context = LocalContext.current

                LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
                    viewModel.showErrorToastChannel.collectLatest { show ->
                        if (show) {
                            Toast.makeText(
                                context, "Erro ao carregar os animais", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                if (animal == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    AnimalDescriptionMenuTeste(animal = animal, viewModel = AEDTOViewModel, personViewModel = PersonViewModel, visitedViewModel = VisitedViewModel) // Passe os dados do animal para a tela
                }
            }
        }
    }
}

