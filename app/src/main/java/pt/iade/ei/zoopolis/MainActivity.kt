package pt.iade.ei.zoopolis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.zoopolis.ui.menus.StartingMenu
import pt.iade.ei.zoopolis.ui.theme.ZoopolisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZoopolisTheme {
                StartingMenu()
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    ZoopolisTheme {
        StartingMenu()
    }
}
@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    ZoopolisTheme {
        MainView()
    }
}