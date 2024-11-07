package com.example.locationfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.locationfinder.ui.theme.LocationFinderTheme

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the database helper to interact with local storage
        dbHelper = DatabaseHelper(this)

        // Set up the main UI content
        setContent {
            LocationFinderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Pass dbHelper to the app content
                    LocationFinderApp(
                        modifier = Modifier.padding(innerPadding),
                        dbHelper = dbHelper
                    )
                }
            }
        }
    }
}

@Composable
fun LocationFinderApp(modifier: Modifier = Modifier, dbHelper: DatabaseHelper? = null) {
    // State variables to hold input data and output messages
    var address by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text field for address input
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Text field for latitude input
        OutlinedTextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Latitude") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Text field for longitude input
        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Longitude") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add a new location to the database
        Button(onClick = {
            if (dbHelper != null && address.isNotBlank() && latitude.isNotBlank() && longitude.isNotBlank()) {
                // Add location to database using dbHelper
                dbHelper.addLocation(address, latitude.toDouble(), longitude.toDouble())
                output = "Location added successfully!" // Success message
            } else {
                output = "Please fill all fields." // Error if any field is blank
            }
        }) {
            Text("Add Location") // Button label
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to search for a location by address in the database
        Button(onClick = {
            if (dbHelper != null) {
                // Search for the location using dbHelper
                val cursor = dbHelper.getLocationByAddress(address)
                if (cursor != null && cursor.moveToFirst()) {
                    // If location is found, retrieve latitude and longitude
                    val lat = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LATITUDE))
                    val lng = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LONGITUDE))
                    output = "Location found: Latitude = $lat, Longitude = $lng" // Display found location
                    cursor.close() // Close cursor after use
                } else {
                    output = "Location not found." // Display message if location not found
                }
            }
        }) {
            Text("Search Location") // Button label
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to update an existing location in the database
        Button(onClick = {
            if (dbHelper != null && address.isNotBlank() && latitude.isNotBlank() && longitude.isNotBlank()) {
                val id = 1 // Placeholder ID; in a real app, logic would get the actual ID
                dbHelper.updateLocation(id, address, latitude.toDouble(), longitude.toDouble())
                output = "Location updated successfully!" // Success message
            } else {
                output = "Please fill all fields." // Error if any field is blank
            }
        }) {
            Text("Update Location") // Button label
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to delete a location by ID in the database
        Button(onClick = {
            if (dbHelper != null && address.isNotBlank()) {
                val id = 1 // Placeholder ID; replace with actual ID in real app
                dbHelper.deleteLocation(id)
                output = "Location deleted successfully!" // Success message
            } else {
                output = "Please enter an address to delete." // Prompt to enter address
            }
        }) {
            Text("Delete Location") // Button label
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Displays the output message based on the action performed
        Text(output)
    }
}

@Preview(showBackground = true)
@Composable
fun LocationFinderAppPreview() {
    LocationFinderTheme {
        // Preview mode: show app without database helper
        LocationFinderApp()
    }
}