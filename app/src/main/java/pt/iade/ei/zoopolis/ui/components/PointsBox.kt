package pt.iade.ei.zoopolis.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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


@Composable
fun PointsBox(points: Int) {
    Card(
        modifier = Modifier
            .padding(top = 15.dp, end = 8.dp, start = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF58A458)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp,
        )
    ){

        Row(
            modifier = Modifier
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically // Ajuste o alinhamento vertical
        ) {
            Text(
                text = "$points points",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xFF0D4311),
                        offset = Offset(4f, 4f),
                        blurRadius = 0.01f
                    )
                )
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PointsBoxPreview(){
    PointsBox(100)

}


