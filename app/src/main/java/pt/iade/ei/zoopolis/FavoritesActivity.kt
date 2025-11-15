package pt.iade.ei.zoopolis

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.collectLatest
import pt.iade.ei.zoopolis.retrofit.FavoriteRepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.RetrofitInstance
import pt.iade.ei.zoopolis.ui.menus.FavoritesMenu
import pt.iade.ei.zoopolis.ui.theme.ZoopolisTheme
import pt.iade.ei.zoopolis.viewmodel.FavoriteViewModel

class FavoriteMenuActivity : ComponentActivity() {

    private val viewModel by viewModels<FavoriteViewModel>(factoryProducer = {
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
                val favoriteAnimalsList = viewModel.favoriteAnimals.collectAsState().value
                val context = LocalContext.current
                LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
                    viewModel.showErrorToastChannel.collectLatest { show ->
                        if (show) {
                            Toast.makeText(
                                context, "Erro ao carregar os animais favoritos", Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
                    FavoritesMenu()
            }

        }
    }
}
