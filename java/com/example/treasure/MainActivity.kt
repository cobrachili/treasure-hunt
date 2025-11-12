/*
Stephan Gonzales / gonzstep@oregonstate.edu
CS 492 / Oregon state University

 */
package com.example.treasure

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.treasure.ui.theme.AppNavigation
import com.example.treasure.ui.theme.TreasureTheme
import com.example.treasure.ui.theme.TreasureViewModel
import com.example.treasure.ui.theme.formatTime
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the FusedLocationProviderClient before setting the content
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            TreasureTheme {
                PermissionsPage(fusedLocationClient)
            }
        }
    }
}

// Get permissions by clicking button
@Composable
fun PermissionsPage(fusedLocationClient: FusedLocationProviderClient) {
    val context = LocalContext.current

    var permissionGranted by remember { mutableStateOf(false) }
    var permissionButton by remember { mutableStateOf(false) }


    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }
    // Your UI content here, e.g., based on permissionGranted and location
    if (permissionGranted) {
        AppNavigation(fusedLocationClient = fusedLocationClient)
    } else {
        // Show permission rationale or UI
        Column ( modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = stringResource(R.string.permissions))
            Button(onClick = { permissionButton = true }) {
                Text(text = stringResource(R.string.grant))
            }
            if (permissionButton) {
                LaunchedEffect(Unit) {
                    when {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            permissionGranted = true
                        }

                        else -> {
                            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                }
            }
        }
    }
}

// Setup timer to display
@Composable
fun TimerContent(TreasureViewModel: TreasureViewModel) {
    val timerValue by TreasureViewModel.timer.collectAsState()

    Timer(
        timerValue = timerValue
    )
}

@Composable
fun Timer(
    timerValue: Long
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = timerValue.formatTime(), fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

    }
}