/*
Stephan Gonzales / gonzstep@oregonstate.edu
CS 492 / Oregon state University

 */
package com.example.treasure.ui.theme


import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.treasure.R
import com.example.treasure.TimerContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority

// First clue page
@Composable
fun FirstCluePage(navController: NavController,
                  TreasureViewModel: TreasureViewModel,
                  fusedLocationClient: FusedLocationProviderClient) {
    var distance by remember { mutableStateOf<Double?>(null) }
    val clue1lat = 33.942221
    val clue1long = -118.403606
    var hint1 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.clue_page))
        Spacer(Modifier.height(20.dp))
        Text(text = stringResource(R.string.clue1))
        Spacer(Modifier.height(20.dp))
        // display timer
        TimerContent(TreasureViewModel)
        // toggle hint
        Button(onClick = { hint1 = true }) {
            Text(text = stringResource(R.string.hint_button))}
        if (hint1) {
            Text(text = stringResource(R.string.hint1))
        }
        Spacer(Modifier.height(20.dp))
        // get current location
        Button(onClick = {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { loc: Location? ->
                // Calculate distance between locations
                loc?.let {
                    val currentLocation = Geo(it.latitude, it.longitude)
                    val clueLocation = Geo(clue1lat, clue1long)
                    distance = currentLocation.haversine(clueLocation)
                }

            }
        }) {
            Text(text = stringResource(R.string.found))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // if distance is less than 1000m you are at LAX
        distance?.let {
            if (it <= 1000) {
                TreasureViewModel.pauseTimer()
                navController.navigate(route = "solved"
                )
            } else {
                Text(text = stringResource(R.string.wrong))
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            TreasureViewModel.stopTimer()
            navController.navigate("start")
        })
        {
            Text(text = stringResource(R.string.quit_button))
        }

    }
}