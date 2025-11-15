package pt.iade.ei.zoopolis.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.models.AnimalDTO


@Composable
fun AnimalDescriptionBoxTeste(animal: AnimalDTO) {
    val borderStrokeWidthSize = 1.45f
    OutlinedCard(
        modifier = Modifier
            .padding(top = 15.dp, end = 8.dp, start = 8.dp)
            .wrapContentHeight()
            .wrapContentWidth(),// Altura ajustada automaticamente ao conteúdo
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0D4311)),
        border = BorderStroke(borderStrokeWidthSize.dp, Color(0xFFE8FFD2)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp,
        )
    ){

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ){


            // Texto principal
            Text(
                text = animal.description,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp),
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




@Preview(showBackground = true)
@Composable
fun AnimalDescriptionTestePreview(){
    AnimalDescriptionBoxTeste(animal = AnimalDTO(
        id = 1,
        name = "Tiger",
        ciName = "Panthera tigris",
        imageUrl = "ola",
        description = "ola"
    )
    )

}


