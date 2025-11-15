package pt.iade.ei.zoopolis.ui.menus

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.iade.ei.zoopolis.AnimalDescriptionMenuActivity
import pt.iade.ei.zoopolis.MainMenuActivity
import pt.iade.ei.zoopolis.ProfileMenuActivity
import pt.iade.ei.zoopolis.R
import pt.iade.ei.zoopolis.ui.components.AnimalButtonTeste
import pt.iade.ei.zoopolis.viewmodel.FavoriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesMenu() {
    val favoriteViewModel: FavoriteViewModel = viewModel()

    // Coleta os animais favoritos da ViewModel
    val favoriteAnimals by favoriteViewModel.favoriteAnimals.collectAsState()

    // Ação de erro de rede
    val showErrorToast by favoriteViewModel.showErrorToastChannel.collectAsState(initial = false)
    if (showErrorToast) {
        Toast.makeText(LocalContext.current, "Erro ao carregar os animais favoritos", Toast.LENGTH_SHORT).show()
    }
    val context = LocalContext.current

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.mainmenubackground),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            val intent = Intent(context, MainMenuActivity::class.java)
                            context.startActivity(intent)
                        },
                            modifier = Modifier
                                .size(75.dp)
                                .padding(top = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Localized description",
                                tint = Color.White,
                                modifier = Modifier.size(75.dp)
                            )
                        }
                    },
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento entre os ícones
                            modifier = Modifier.padding(top = 20.dp, end = 10.dp) // Padding geral
                        ) {
                            IconButton(
                                onClick =
                                {
                                    val intent = Intent(context, ProfileMenuActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.size(60.dp)

                            )
                            {
                                Icon(
                                    painter = painterResource(R.drawable.account_circle),
                                    contentDescription = "Localized description",
                                    tint = Color.White,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.height(100.dp)
                )
            },
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .align(alignment = Alignment.TopCenter)
            ) {

                Spacer(modifier = Modifier.padding(top = 100.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    items(favoriteAnimals.size) { animal ->
                        AnimalButtonTeste(
                            animal = favoriteAnimals[animal],
                            AnimalDescriptionMenuActivity::class.java, favoriteViewModel
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesMenuPreview() {
    FavoritesMenu()
}


