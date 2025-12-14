package pt.iade.ei.zoopolis.ui.components

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.zoopolis.R
import pt.iade.ei.zoopolis.models.AnimalDTO
import pt.iade.ei.zoopolis.viewmodel.FavoriteViewModel

@Composable
fun AnimalButtonTeste(
    animal: AnimalDTO,
    activityClass: Class<*>,
    viewModel: FavoriteViewModel
) {
    val context = LocalContext.current
    val favoriteStatusMap = viewModel.favoriteStatus.collectAsState().value
    val isFavorite = remember(animal.id, favoriteStatusMap) {
        mutableStateOf(favoriteStatusMap[animal.id] ?: false)
    }

    Log.d("AnimalButtonTeste", "isFavorite for ${animal.id}: ${isFavorite.value}")

    // Get the drawable resource ID directly from the imageUrl field
    val imageResId = remember(animal.imageUrl) {
        Log.d("AnimalButtonTeste", "Animal: '${animal.name}' -> Trying to find resource from imageUrl: '${animal.imageUrl}'")
        val id = context.resources.getIdentifier(animal.imageUrl, "drawable", context.packageName)
        if (id == 0) {
            Log.w("AnimalButtonTeste", "Drawable resource '${animal.imageUrl}' not found. Falling back to default.")
            R.drawable.ic_animal
        } else {
            id
        }
    }

    OutlinedCard(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 8.dp)
            .size(width = 300.dp, height = 100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.45.dp, Color.hsl(124f, 0.68f, 0.16f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        onClick = {
            val intent = Intent(context, activityClass)
            intent.putExtra("animal_id", animal.id)
            context.startActivity(intent)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8FFD2)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        if (isFavorite.value) {
                            viewModel.removeFavorite(animal.id)
                        } else {
                            viewModel.addFavorite(animal.id)
                        }
                    },
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(34.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            if (isFavorite.value) R.drawable.heart_minus else R.drawable.heart_plus
                        ),
                        contentDescription = if (isFavorite.value) "Remove from favorites" else "Add to favorites",
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(2.dp))

                Text(
                    text = animal.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0xFFE8FFD2),
                            offset = Offset(3f, 3f),
                            blurRadius = 0.15f
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.padding(2.dp))

            // Use the local drawable resource
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = animal.name,
                modifier = Modifier.weight(0.65f).fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
