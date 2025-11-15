package pt.iade.ei.zoopolis.ui.menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.iade.ei.zoopolis.LoginMenuActivity
import pt.iade.ei.zoopolis.MainMenuActivity
import pt.iade.ei.zoopolis.R
import pt.iade.ei.zoopolis.RegisterMenuActivity
import pt.iade.ei.zoopolis.ui.components.EnterAsGuest
import pt.iade.ei.zoopolis.ui.components.Login
import pt.iade.ei.zoopolis.viewmodel.PersonViewModel


@Composable

fun StartingMenu(viewModel: PersonViewModel) {

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.startingmenubackground),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            containerColor = Color.Transparent
        ) { innerPadding ->
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
                    Image(
                        painter = painterResource(R.drawable.logozoo),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(300.dp, 300.dp),
                        alignment = Alignment.TopStart
                    )



                    Login("Login", R.drawable.login, LoginMenuActivity::class.java)
                    Login("Register", R.drawable.register, RegisterMenuActivity::class.java)
                    EnterAsGuest("Enter as Guest", R.drawable.guest, MainMenuActivity::class.java, viewModel)

                }
            }
        }
    }
}

