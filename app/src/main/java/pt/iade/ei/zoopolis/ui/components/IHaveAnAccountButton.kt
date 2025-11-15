package pt.iade.ei.zoopolis.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.MainActivity


@Composable
fun IHaveAnAccountButton(name: String, activityClass: Class<*>) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(top = 7.dp, bottom = 10.dp)
            .size(width = 180.dp, height = 40.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp,
        ),
        onClick = {
            val intent = Intent(context, activityClass)
            context.startActivity(intent)
        }
    ){


        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){

                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                )
            }
        }
    }




@Preview(showBackground = true)
@Composable
fun IHaveAnAccountButtonPreview(){
    IHaveAnAccountButton("I Have An Account", MainActivity::class.java)

}


