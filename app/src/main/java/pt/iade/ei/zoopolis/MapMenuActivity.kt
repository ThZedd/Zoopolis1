package pt.iade.ei.zoopolis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.zoopolis.ui.menus.MapMenu
import pt.iade.ei.zoopolis.ui.theme.ZoopolisTheme

class MapMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZoopolisTheme {
                MapMenu()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapMenuActivityPreview() {
    ZoopolisTheme {
        MapMenu()
    }
}