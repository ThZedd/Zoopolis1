package pt.iade.ei.zoopolis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.zoopolis.ui.menus.ActivitiesMenu
import pt.iade.ei.zoopolis.ui.theme.ZoopolisTheme

class ActivitiesMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZoopolisTheme {
                ActivitiesMenu()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivitiesMenuActivityPreview() {
    ZoopolisTheme {
        ActivitiesMenu()
    }
}