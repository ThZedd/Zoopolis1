package pt.iade.ei.zoopolis.ui.menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.zoopolis.R
import androidx.compose.ui.res.painterResource as painterResource


@Composable
fun MapMenu() {
    Scaffold { innerPadding ->
        LazyRow(modifier = Modifier.padding(innerPadding)) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.mapazoo),
                    contentDescription = "Description of the image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable

fun MapMenuPreview(){
    MapMenu()

}