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
fun ActivitiesTimeBox(name: String, imageRes: Int, time: String) {
    val borderStrokeWidthSize = 2f
    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 0.dp)
            .size(width = 600.dp, height = 150.dp),
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
                contentAlignment = Alignment.TopCenter
            ) {
                Image(painter = painterResource(id = imageRes),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()

                )
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment =Alignment.CenterHorizontally ,
                        verticalArrangement = Arrangement.spacedBy(0.dp))
                    {
                    // Texto principal
                    Text(
                        text = name,
                        fontWeight = FontWeight.W900,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 6.dp),
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
                            text = time,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 0.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = TextStyle(
                                lineHeight = 20.sp,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(4f, 4f),
                                    blurRadius = 5f
                                )
                            )
                        )
                        Text(
                            text = "Duração: 20 minutos ",
                            fontWeight = FontWeight.W900,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 0.dp, bottom = 10.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(4f, 4f),
                                    blurRadius = 5f
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
fun ActivitiesTimeBoxPreview(){
    ActivitiesTimeBox("Horário:", R.drawable.baia_dos_golfinhos_design_horario,"21 de setembro a 20 março: 11h (não se realiza à terça-feira) / 14h30\n" +
            "21 de março a 20 de setembro: 11h / 14h30 / 16h30\n")

}


