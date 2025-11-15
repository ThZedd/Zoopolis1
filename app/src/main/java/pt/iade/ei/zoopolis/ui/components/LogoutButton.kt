package pt.iade.ei.zoopolis.ui.components



import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.MainActivity
import pt.iade.ei.zoopolis.viewmodel.PersonViewModel


@Composable
fun LogoutButton(name: String, personViewModel: PersonViewModel) {
    val borderStrokeWidthSize = 1.45f
    val context = LocalContext.current
    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 8.dp)
            .size(width = 165.dp, height = 50.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent),
        border = BorderStroke(borderStrokeWidthSize.dp, Color.Red),
        shape = RoundedCornerShape(100),
        onClick = {

            personViewModel.logout()

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

            (context as? androidx.activity.ComponentActivity)?.finish()
        }
    ){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {


                Box {
                    // Texto principal
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f), // Ajuste o deslocamento da sombra
                                blurRadius = 2f // Aumente o valor para uma sombra mais suave
                            )
                        )
                    )
                }
            }
        }
    }





