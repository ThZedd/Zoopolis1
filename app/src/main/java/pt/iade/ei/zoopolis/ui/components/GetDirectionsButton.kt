package pt.iade.ei.zoopolis.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import pt.iade.ei.zoopolis.GoogleMapsActivity
import pt.iade.ei.zoopolis.models.AnimalDTO


@Composable
fun GetDirectionsButton(name: String, containerColor: Color, animal: AnimalDTO) {
    val borderStrokeWidthSize = 1.45f
    val context = LocalContext.current
    OutlinedCard(
        modifier = Modifier
            .padding(top = 15.dp, end = 8.dp, start = 8.dp)
            .size(width = 330.dp, height = 60.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor),
        border = BorderStroke(borderStrokeWidthSize.dp, Color(0xFFE8FFD2)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp,

            ),
        onClick = {
            val intent = Intent(context,GoogleMapsActivity::class.java)
            intent.putExtra("animal_id", animal.id)
            context.startActivity(intent)
            context.startActivity(intent)
        }
    ){


        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){

                Box {
                    // Contorno - Desenha o texto em todas as direções para simular o contorno
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
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
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(3f, 3f), // Ajuste o deslocamento da sombra
                                blurRadius = 0.75f // Aumente o valor para uma sombra mais suave
                            )
                        )
                    )
                }
            }
        }
    }





