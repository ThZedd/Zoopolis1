package pt.iade.ei.zoopolis.ui.menus

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import pt.iade.ei.zoopolis.models.AnimalDTO
import pt.iade.ei.zoopolis.ui.components.AnimalButtonTeste
import pt.iade.ei.zoopolis.viewmodel.FavoriteViewModel
import java.text.Normalizer

fun String.prepareForSearch(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        .replace("-", " ")
        .lowercase()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalMenuTeste(
    animals: List<AnimalDTO>,
    favoriteViewModel: FavoriteViewModel
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

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
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                val intent = Intent(context, MainMenuActivity::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.size(75.dp).padding(top = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home",
                                tint = Color.White,
                                modifier = Modifier.size(75.dp)
                            )
                        }
                    },
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                        ) {
                            /*IconButton(
                                onClick = {
                                    val intent = Intent(context, FavoriteMenuActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.size(60.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.favorite_heart_menu),
                                    contentDescription = "Favorites",
                                    tint = Color.White,
                                    modifier = Modifier.size(60.dp)
                                )
                            }*/
                            IconButton(
                                onClick = {
                                    val intent = Intent(context, ProfileMenuActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.size(60.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.account_circle),
                                    contentDescription = "Profile",
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
                SearchBar(
                    modifier = Modifier.padding(15.dp, 80.dp, 15.dp, 15.dp),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { active = false },
                    active = false,
                    onActiveChange = { active = false },
                    placeholder = { Text(text = "Search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (text.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable { text = "" },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear"
                            )
                        }
                    }
                ) { }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    val filteredAnimals = animals.filter { animal ->
                        animal.name.prepareForSearch().contains(text.prepareForSearch())
                    }

                    items(filteredAnimals) { animal ->
                        AnimalButtonTeste(
                            animal = animal,
                            activityClass = AnimalDescriptionMenuActivity::class.java,
                            viewModel = favoriteViewModel
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimalMenuPreview() {
    AnimalMenuTeste(emptyList(), viewModel())
}
