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

@Composable
fun SecondCluePage(navController: NavController,
                  TreasureViewModel: TreasureViewModel,
                  fusedLocationClient: FusedLocationProviderClient) {
    var distance by remember { mutableStateOf<Double?>(null) }
    val clue2lat = 34.037206
    val clue2long = -118.676642
    var hint1 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.clue_page))
        Spacer(Modifier.height(20.dp))
        Text(text = stringResource(R.string.clue2))
        Spacer(Modifier.height(20.dp))
        TimerContent(TreasureViewModel)
        Button(onClick = { hint1 = true }) {
            Text(text = stringResource(R.string.hint_button))}
        if (hint1) {
            Text(text = stringResource(R.string.hint2))
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { loc: Location? ->
                loc?.let {
                    val currentLocation = Geo(it.latitude, it.longitude)
                    val clueLocation = Geo(clue2lat, clue2long)
                    distance = currentLocation.haversine(clueLocation)
                }

            }
        }) {
            Text(text = stringResource(R.string.found))
        }

        Spacer(modifier = Modifier.height(16.dp))
        distance?.let {
            // if distance is less than 25m you are at the malibu pier
            if (it <= 25) {
                TreasureViewModel.pauseTimer()
                navController.navigate(route = "completed"
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