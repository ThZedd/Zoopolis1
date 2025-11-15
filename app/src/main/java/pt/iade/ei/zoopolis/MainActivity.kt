package pt.iade.ei.zoopolis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.iade.ei.zoopolis.models.SessionManager
import pt.iade.ei.zoopolis.retrofit.PersonRepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.RetrofitInstance
import pt.iade.ei.zoopolis.ui.menus.StartingMenu
import pt.iade.ei.zoopolis.ui.theme.ZoopolisTheme
import pt.iade.ei.zoopolis.viewmodel.PersonViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<PersonViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PersonViewModel(
                    PersonRepositoryImplementation(RetrofitInstance.api),
                    applicationContext)
                        as T
            }
        }
    })

    // Iniciar o SessionManager
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {

        sessionManager = SessionManager(applicationContext)
        super.onCreate(savedInstanceState)
        RetrofitInstance.initializeSessionManager(applicationContext)
        enableEdgeToEdge()
        setContent {
            ZoopolisTheme {
                StartingMenu(viewModel = viewModel)
            }
        }
    }
}

