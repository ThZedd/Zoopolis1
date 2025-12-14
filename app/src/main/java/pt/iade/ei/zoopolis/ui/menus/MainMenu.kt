package pt.iade.ei.zoopolis.ui.menus

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.ActivitiesMenuActivity
import pt.iade.ei.zoopolis.AnimalMenuActivity
import pt.iade.ei.zoopolis.FavoriteMenuActivity
import pt.iade.ei.zoopolis.GoogleMapsActivity
import pt.iade.ei.zoopolis.MainMenuActivity
import pt.iade.ei.zoopolis.PricesMenuActivity
import pt.iade.ei.zoopolis.ProfileMenuActivity
import pt.iade.ei.zoopolis.R
import pt.iade.ei.zoopolis.ui.components.EnterTheCodeButton
import pt.iade.ei.zoopolis.ui.components.MenuButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu() {
    val borderStrokeWidthSize = 1.45f
    var code by remember { mutableStateOf("") }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var theCode by remember { mutableStateOf(false) }
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
            if (!theCode) {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            MenuButton(
                                "Animais",
                                R.drawable.animais,
                                AnimalMenuActivity::class.java
                            )
                            MenuButton(
                                "Atividades",
                                R.drawable.atividades,
                                ActivitiesMenuActivity::class.java
                            )
                        }

                        Row {
                            MenuButton("Mapa", R.drawable.mapazoo, GoogleMapsActivity::class.java)
                            MenuButton("Preços", R.drawable.precos, PricesMenuActivity::class.java)
                        }

                        OutlinedCard(
                            modifier = Modifier
                                .padding(vertical = 15.dp, horizontal = 8.dp)
                                .size(width = 315.dp, height = 80.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(borderStrokeWidthSize.dp, Color.hsl(124f, 0.68f, 0.16f)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 7.dp
                            ),
                            onClick = {
                                theCode = true
                            }
                        ){


                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(painter = painterResource(id = R.drawable.enterthecode),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()

                                    )

                                    Box {
                                        // Contorno - Desenha o texto em todas as direções para simular o contorno
                                        Text(
                                            text = "Enter The Code",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color(0xFF0D4311), // Cor do contorno
                                            modifier = Modifier.offset(x = 1.dp, y = 2.dp)
                                        )
                                        Text(
                                            text = "Enter The Code",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color(0xFF0D4311), // Cor do contorno
                                            modifier = Modifier.offset(x = -1.dp, y = 0.dp)
                                        )
                                        Text(
                                            text = "Enter The Code",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color(0xFF0D4311), // Cor do contorno
                                            modifier = Modifier.offset(x = 0.dp, y = 1.dp)
                                        )
                                        Text(
                                            text = "Enter The Code",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color(0xFF0D4311), // Cor do contorno
                                            modifier = Modifier.offset(x = 0.dp, y = -1.dp)
                                        )

                                        // Texto principal
                                        Text(
                                            text = "Enter The Code",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color.White,
                                            style = TextStyle(
                                                shadow = Shadow(
                                                    color = Color.Black,
                                                    offset = Offset(4f, 4f), // Ajuste o deslocamento da sombra
                                                    blurRadius = 6f // Aumente o valor para uma sombra mais suave
                                                )
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            MenuButton(
                                "Animais",
                                R.drawable.animais,
                                AnimalMenuActivity::class.java
                            )
                            MenuButton(
                                "Atividades",
                                R.drawable.atividades,
                                ActivitiesMenuActivity::class.java
                            )
                        }

                        Row {
                            MenuButton("Mapa", R.drawable.mapazoo, GoogleMapsActivity::class.java)
                            MenuButton("Preços", R.drawable.precos, PricesMenuActivity::class.java)
                        }

                        EnterTheCodeButton("Enter The Code", R.drawable.enterthecode)
                    }
                }
                Box(

                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 0.dp)

                ) {

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Card(modifier = Modifier
                            .padding(top = 480.dp)
                            .fillMaxSize(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF58A458))
                        ){
                            Column(
                                modifier = Modifier.padding(top = 20.dp, start = 55.dp, end = 55.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = "Code",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 8.dp, start = 3.dp),
                                    textAlign = TextAlign.Start,
                                    color = Color.White
                                )
                                pt.iade.ei.zoopolis.ui.components.TextField(
                                    value = code,
                                    onValueChange = { code = it },
                                    labelText = "Code",
                                    leadingIcon = Icons.Default.ThumbUp,
                                    onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center)
                                {
                                    Card(modifier = Modifier
                                        .padding(top = 7.dp, bottom = 10.dp)
                                        .size(width = 115.dp, height = 40.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 7.dp,
                                        ),
                                        onClick = {
                                            code = ""
                                            theCode = false
                                        }
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Box {

                                                // Texto principal
                                                Text(
                                                    text = "Cancel",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Black,
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Card(modifier = Modifier
                                        .padding(top = 7.dp, bottom = 10.dp)
                                        .size(width = 180.dp, height = 40.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFF1E1E1E)
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 7.dp,
                                        ),
                                        onClick = {
                                            code = ""
                                            theCode = false
                                        }
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box {
                                                // Contorno - Desenha o texto em todas as direções para simular o contorno
                                                Text(
                                                    text = "Enter",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Black, // Contorno geralmente é preto
                                                    style = TextStyle(
                                                        shadow = Shadow(
                                                            color = Color.Black,
                                                            offset = Offset(-3f, -3f),
                                                            blurRadius = 0.75f
                                                        )
                                                    )
                                                )

                                                // Texto principal
                                                Text(
                                                    text = "Enter",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    style = TextStyle(
                                                        shadow = Shadow(
                                                            color = Color.Black,
                                                            offset = Offset(
                                                                3f,
                                                                3f
                                                            ), // Ajuste o deslocamento da sombra
                                                            blurRadius = 0.75f // Aumente o valor para uma sombra mais suave
                                                        )
                                                    )
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.padding(end = 20.dp))
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    MainMenu()
}
