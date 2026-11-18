package com.example.campus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campus.ui.theme.CampusTheme

private const val TAG = "Campus"

data class Event(
    val id: String,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int
)

val sampleEvents = listOf(
    Event("1", "Conférence Ia", "Tech", "Amphi A", 45),
    Event("2", "Brexit", "Politique", "Gymnase", 0), // Complet
    Event("3", "Conférence Cyber", "Tech", null, 120), // Sans lieu
    Event("4", "Atelier peinture", "Culture", "Salle 12", 10),
    Event("5", "Cousinade party", "Détente", "Foyer", 30)
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

@Preview(showBackground = true)
@Composable
fun EventCardFullPreview() {
    CampusTheme {
        EventCard(
            event = Event("2", "Brexit", "Politique", "Gymnase", 0),
            onClick = {},
            onBook = {}
        )
    }
}