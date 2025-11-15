package pt.iade.ei.zoopolis.menusdasatividadesteste

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.zoopolis.FavoriteMenuActivity
import pt.iade.ei.zoopolis.MainMenuActivity
import pt.iade.ei.zoopolis.ProfileMenuActivity
import pt.iade.ei.zoopolis.R
import pt.iade.ei.zoopolis.ui.components.ActivitiesDescriptionBox
import pt.iade.ei.zoopolis.ui.components.ActivitiesImageBox
import pt.iade.ei.zoopolis.ui.components.ActivitiesTimeBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Bosque() {
    val context = LocalContext.current
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.bosque_encantado_folhas_fundo_design),
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
                                    val intent = Intent(context, FavoriteMenuActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            {
                                Icon(
                                    painter = painterResource(R.drawable.favorite_heart_menu),
                                    contentDescription = "Localized description",
                                    tint = Color.White,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
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
            Box(

                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)

            ) {

                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        ActivitiesImageBox("Bosque Encantado", R.drawable.bosque_encantado_design_banner_passaro_dentro_retangulo,Color.hsl(196f, 0.93f, 0.63f))
                        ActivitiesTimeBox("Horário:", R.drawable.bosque_encantado_design_banner_horario_sem_transparencia, "\nVerão e inverno: 12h30 / 16h\n\n")
                        ActivitiesDescriptionBox("Descrição:", R.drawable.bosque_encantado_folhas_fundo_design_banner_info, "Na apresentação Bosque Encantado, privilegia-se a proximidade do treinador e do animal junto do público.\n" +
                                "\n" +
                                "Os visitantes podem observar o voo das aves tendo uma sensação única de contacto com a vida animal, com especial destaque à exuberância das espécies tropicais. A apresentação dá a conhecer as diferenças e a importância de existirem, presas e predadores, aves diurnas e noturnas, entre tantas outras informações. Encontre ainda alguns mamíferos e, por vezes, répteis, e fique a saber mais sobre as espécies aqui representadas e os seus ecossistemas.\n" +
                                "\n" +
                                "Porque se trata de biodiversidade, é dado a conhecer ao público uma grande variabilidade de espécies, cuja presença nas apresentações acontece de forma rotativa. O bem-estar animal é uma preocupação, do Jardim Zoológico, que também se reflete no Bosque Encantado.")


                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable

fun BosquePreview(){
    Bosque()

}

