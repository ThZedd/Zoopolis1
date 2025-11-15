package pt.iade.ei.zoopolis.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EditProfileTextFieldBox() {
    val focusManager = LocalFocusManager.current
    Card(
        modifier = Modifier
            .padding(top = 15.dp, end = 8.dp, start = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF58A458)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp,
        )
    ){

        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center // Ajuste o alinhamento vertical
        ) {
            Text(
                text = "Username",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp, start = 3.dp),
                textAlign = TextAlign.Start,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xFF0D4311),
                        offset = Offset(4f, 4f),
                        blurRadius = 0.01f
                    )
                )
            )
            TextField(
                value = "",
                onValueChange = {},
                labelText = "Username",
                leadingIcon = Icons.Default.Person,
                onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) }
            )
            EditProfileChangeButton("Change")
            Text(
                text = "Email",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp, start = 3.dp),
                textAlign = TextAlign.Start,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xFF0D4311),
                        offset = Offset(4f, 4f),
                        blurRadius = 0.01f
                    )
                )
            )
            TextField(
                value = "",
                onValueChange = {},
                labelText = "Email",
                leadingIcon = Icons.Default.Person,
                onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) }
            )
            EditProfileChangeButton("Change")
            Text(
                text = "Password",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp, start = 3.dp),
                textAlign = TextAlign.Start,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xFF0D4311),
                        offset = Offset(4f, 4f),
                        blurRadius = 0.01f
                    )
                )
            )
            TextField(
                value = "",
                onValueChange = {},
                labelText = "Password",
                leadingIcon = Icons.Default.Lock,
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(), // Exibe asteriscos
                onNext = { focusManager.clearFocus() }
            )
            EditProfileChangeButton("Change")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EditProfileTextFieldBoxPreview(){
    EditProfileTextFieldBox()

}


