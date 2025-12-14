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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.MainActivity
import pt.iade.ei.zoopolis.R
import pt.iade.ei.zoopolis.models.Person
import pt.iade.ei.zoopolis.retrofit.Result
import pt.iade.ei.zoopolis.ui.components.IHaveAnAccountButton
import pt.iade.ei.zoopolis.ui.components.TextField
import pt.iade.ei.zoopolis.viewmodel.PersonViewModel

@Composable
fun RegisterMenu(viewModel: PersonViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf('M') }
    val genders = listOf('M', 'F', 'O')
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // Regex para validar a password (mesma lógica do backend)
    // 1 numero, 1 maiuscula, 1 especial
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$"

    val registerResult by viewModel.registerResult.observeAsState()

    // 1. A navegação só acontece AQUI, se o resultado for SUCESSO
    LaunchedEffect(registerResult) {
        when (val result = registerResult) {
            is Result.Success -> {
                Toast.makeText(context, "Registo bem-sucedido!", Toast.LENGTH_SHORT).show()
                // Só navegamos para a MainActivity se o registo funcionar
                val intent = Intent(context, MainActivity::class.java)
                // Flags para impedir que o utilizador volte ao login com o botão "voltar"
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
            is Result.Error -> {
                Toast.makeText(context, "Erro: ${result.message}", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.startingmenubackground),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(containerColor = Color.Transparent) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(top = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ... (O teu código de Imagem e Logo mantém-se igual aqui) ...
                Image(
                    painter = painterResource(R.drawable.logozoo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(250.dp),
                    alignment = Alignment.TopStart
                )

                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF58A458))
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        item {
                            // ... (Campos de Username, Email, Password mantêm-se iguais) ...
                            Text("Username", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp, start = 3.dp), textAlign = TextAlign.Start, color = Color.White)
                            TextField(value = username, onValueChange = { username = it }, labelText = "Username", leadingIcon = Icons.Default.Person, onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) })

                            Text("Email", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp, start = 3.dp), textAlign = TextAlign.Start, color = Color.White)
                            TextField(value = email, onValueChange = { email = it }, labelText = "Email", leadingIcon = Icons.Default.Person, onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) })

                            Text("Password", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp, start = 3.dp), textAlign = TextAlign.Start, color = Color.White)
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

                            // ... (Seleção de género mantém-se igual) ...
                            Text("Gender", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                            Row {
                                genders.forEach { gender ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.selectable(selected = (selectedGender == gender), onClick = { selectedGender = gender }).padding(8.dp)
                                    ) {
                                        RadioButton(selected = (selectedGender == gender), onClick = { selectedGender = gender }, colors = RadioButtonDefaults.colors(selectedColor = Color.White, unselectedColor = Color.hsl(124f, 0.68f, 0.16f)))
                                        Text(text = gender.toString(), color = Color.White, modifier = Modifier.padding(start = 8.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row {
                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                IHaveAnAccountButton("I Have An Account", MainActivity::class.java)
                                Spacer(modifier = Modifier.width(10.dp))

                                // BOTÃO REGISTER
                                Card(
                                    modifier = Modifier.padding(top = 7.dp, bottom = 10.dp).size(width = 180.dp, height = 40.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
                                    onClick = {
                                        // 2. VERIFICAÇÃO ANTES DE ENVIAR
                                        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

                                            // Verifica se a password cumpre o REGEX
                                            if (password.matches(Regex(passwordPattern))) {
                                                val person = Person(
                                                    name = username,
                                                    email = email,
                                                    password = password,
                                                    gender = selectedGender,
                                                    points = 0
                                                )
                                                // Chama o ViewModel e espera pelo LaunchedEffect
                                                viewModel.register(person)

                                                // NOTA: Removi o startActivity daqui.
                                                // Ele agora acontece lá em cima no LaunchedEffect
                                            } else {
                                                // AVISO AO UTILIZADOR (Fica na mesma página)
                                                Toast.makeText(context, "A password requer: 1 Maiúscula, 1 Número, 1 Caracter Especial", Toast.LENGTH_LONG).show()
                                            }

                                        } else {
                                            Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                ) {
                                    // ... (Conteúdo visual do botão mantém-se igual) ...
                                    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                        Box {
                                            Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center, color = Color.Black, style = TextStyle(shadow = Shadow(color = Color.Black, offset = Offset(-3f, -3f), blurRadius = 0.75f)))
                                            Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center, color = Color.White, style = TextStyle(shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 0.75f)))
                                        }
                                    }
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