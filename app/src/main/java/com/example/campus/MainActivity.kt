package com.example.campus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campus.ui.theme.CampusTheme

private const val TAG = "Campus"

data class Event(
    val id: String,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int,
    val description: String
)

val sampleEvents = listOf(
    Event("1", "Conférence Ia", "Tech", "Amphi A", 45, "Découverte des modèles d'intelligence artificielle générative sur le campus."),
    Event("2", "Brexit", "Politique", "Gymnase", 0, "Table ronde et débat autour des conséquences économiques et politiques du Brexit."),
    Event("3", "Conférence Cyber", "Tech", null, 120, "Atelier pratique de sensibilisation à la cybersécurité et à la protection des données."),
    Event("4", "Atelier peinture", "Culture", "Salle 12", 10, "Initiation à l'aquarelle et à la peinture acrylique ouverte à tous les étudiants."),
    Event("5", "Cousinade party", "Détente", "Foyer", 30, "Soirée conviviale avec jeux de société, musique et buffet partagé au foyer.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusTheme {
                EventListScreen(events = sampleEvents)
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title)
            Text(text = event.category)
            Text(text = event.location ?: "Lieu à confirmer")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = if (event.capacity == 0) "Complet" else "${event.capacity} places")
                Button(
                    onClick = onBook,
                    enabled = event.capacity > 0
                ) {
                    Text("Réserver")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(events: List<Event>) {
    val categories = listOf("Toutes", "Tech", "Politique", "Culture", "Détente")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Campus") })
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = category == "Toutes",
                            onClick = { Log.d(TAG, "Catégorie : $category") },
                            label = { Text(category) }
                        )
                    }
                }
            }

            items(events, key = { it.id }) { event ->
                EventCard(
                    event = event,
                    onClick = { Log.d(TAG, "Clic sur ${event.title}") },
                    onBook = { Log.d(TAG, "Réservation pour ${event.title}") }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    event: Event,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(event.title) })
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = event.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = event.title)
            Text(text = event.category)
            Text(text = event.location ?: "Lieu à confirmer")
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = event.description,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onBook,
                enabled = event.capacity > 0,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (event.capacity == 0) "Complet" else "Réserver")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardFullPreview() {
    CampusTheme {
        EventCard(
            event = Event("2", "Brexit", "Politique", "Gymnase", 0, "Table ronde autour du Brexit."),
            onClick = {},
            onBook = {}
        )
    }
}