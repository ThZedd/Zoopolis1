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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.MainMenuActivity
import pt.iade.ei.zoopolis.R
import pt.iade.ei.zoopolis.models.LoginRequestDTO
import pt.iade.ei.zoopolis.retrofit.Result
import pt.iade.ei.zoopolis.ui.components.TextField
import pt.iade.ei.zoopolis.viewmodel.PersonViewModel

@Composable
fun LoginMenu(viewModel: PersonViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // Observa o resultado do login
    val loginResponse by viewModel.loginResult.observeAsState()

    var isLoading by remember { mutableStateOf(false) }

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
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.logozoo),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(300.dp),
                        alignment = Alignment.TopStart
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(top = 300.dp)
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF58A458)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Email",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 3.dp),
                            textAlign = TextAlign.Start,
                            color = Color.White
                        )
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            labelText = "Email",
                            leadingIcon = Icons.Default.Person,
                            onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) }
                        )

                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 3.dp),
                            textAlign = TextAlign.Start,
                            color = Color.White
                        )
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            labelText = "Password",
                            leadingIcon = Icons.Default.Lock,
                            keyboardType = KeyboardType.Password,
                            visualTransformation = PasswordVisualTransformation(),
                            onNext = { focusManager.clearFocus() }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .padding(top = 7.dp, bottom = 10.dp)
                                .size(width = 280.dp, height = 40.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1E1E1E)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 7.dp,
                            ),
                            onClick = {
                                isLoading = true
                                // Chama a função de login
                                val loginRequestDTO = LoginRequestDTO(email, password)
                                viewModel.login(loginRequestDTO)
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(color = Color.White)
                                } else {
                                    Text(
                                        text = "Sign In",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Observa o estado de login
    when (val result = loginResponse) {
        is Result.Success -> {
            // Se login for bem-sucedido, redireciona
            isLoading = false
            val intent = Intent(context, MainMenuActivity::class.java)
            context.startActivity(intent)
        }
        is Result.Error -> {
            // Exibe a mensagem de erro
            isLoading = false
            Toast.makeText(context, result.message ?: "Login failed", Toast.LENGTH_SHORT).show()
        }
        else -> {
            // Para garantir que o "when" seja exaustivo, você pode adicionar esse ramo else
            // que pode ser usado caso algum estado inesperado aconteça
            isLoading = false
        }
    }
}











