package pt.iade.ei.zoopolis.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.R


@Composable
fun ActivitiesImageBox(name: String, imageRes: Int,containerColor: Color){
    val borderStrokeWidthSize = 2f
    OutlinedCard(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 20.dp)
            .size(width = 500.dp, height = 180.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(borderStrokeWidthSize.dp, containerColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        ),

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
                                blurRadius = 5f // Aumente o valor para uma sombra mais suave
                            )
                        )
                    )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ActivitiesImageBoxPreview(){

    ActivitiesImageBox("Baia dos Golfinhos", R.drawable.baia_dos_golfinhos_design_penguin_dentro_do_retangulo,Color.hsl(196f, 0.93f, 0.63f))

}