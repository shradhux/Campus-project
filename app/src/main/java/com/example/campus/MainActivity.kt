package com.example.campus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.campus.ui.theme.CampusTheme

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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CampusTheme {
        Greeting("Android")
    }
}