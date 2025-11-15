package pt.iade.ei.zoopolis.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.MainActivity
import pt.iade.ei.zoopolis.R


@Composable
fun PriceButton(name: String, imageRes: Int, activityClass: Class<*>, link: String? = null){
    val borderStrokeWidthSize = 1.45f
    val context = LocalContext.current
    val lintent = remember { Intent(Intent.ACTION_VIEW, android.net.Uri.parse(link)) }
    OutlinedCard(
        modifier = Modifier
            .padding(start = 15.dp, end = 8.dp, bottom = 20.dp)
            .size(width = 500.dp, height = 180.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(borderStrokeWidthSize.dp, Color.hsl(0f, 0f, 0f)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        ),
        onClick = {
            if (link != null) {
                context.startActivity(lintent)
            } else{
            val intent = Intent(context, activityClass)
            context.startActivity(intent)
            }
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
                Image(painter = painterResource(id = imageRes),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()

                )
                Box {
                    // Contorno - Desenha o texto em todas as direções para simular o contorno
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF0D4311), // Cor do contorno
                        modifier = Modifier.offset(x = 1.dp, y = 2.dp)
                    )
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF0D4311), // Cor do contorno
                        modifier = Modifier.offset(x = -1.dp, y = 0.dp)
                    )
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF0D4311), // Cor do contorno
                        modifier = Modifier.offset(x = 0.dp, y = 1.dp)
                    )
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF0D4311), // Cor do contorno
                        modifier = Modifier.offset(x = 0.dp, y = -1.dp)
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
@Preview(showBackground = true)
@Composable
fun PriceButtonPreview(){

    PriceButton("Buy Tickets", R.drawable.discount, MainActivity::class.java)

}