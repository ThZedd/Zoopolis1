package pt.iade.ei.zoopolis.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun ActivitiesDescriptionBox(name: String, imageRes: Int, description: String) {
    val borderStrokeWidthSize = 2f
    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 0.dp)
            .size(width = 500.dp, height = 630.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(borderStrokeWidthSize.dp, Color.hsl(196f, 0.93f, 0.63f)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp,

            )
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
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()

                )
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment =Alignment.CenterHorizontally ,
                    verticalArrangement = Arrangement.Center)
                {
                    // Texto principal
                    Text(
                        text = name,
                        fontWeight = FontWeight.W900,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(top = 0.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 6f
                            )
                        )
                    )

                    Text(
                        text = description,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 0.dp, end = 6.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = TextStyle(
                            lineHeight = 25.sp,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 0.15f
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
fun ActivitiesDescriptionBoxPreview(){
    ActivitiesDescriptionBox(
        "Descrição:",
               R.drawable.activitiesdescriptionbox,
        "A valorização dos oceanos e a conservação da biodiversidade marinha, é a mensagem das apresentações diárias da Baía dos Golfinhos. \n" +
            "\n" +
            "Numa envolvência única, de comportamentos subaquáticos, os visitantes têm a oportunidade de conhecer as capacidades físicas e mentais dos embaixadores dos oceanos - os golfinhos. Com esta apresentação pretende-se ainda, sensibilizar os visitantes para a maior ameaça dos oceanos – o lixo marinho – e, deste modo, promover a mudança de atitudes e comportamentos.\n" +
            "\n" +
            "Conhecer para cuidar e agir pela preservação dos oceanos!")

}


