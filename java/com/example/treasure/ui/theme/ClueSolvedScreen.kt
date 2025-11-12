/*
Stephan Gonzales / gonzstep@oregonstate.edu
CS 492 / Oregon state University

 */
package com.example.treasure.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.treasure.R
import com.example.treasure.TimerContent

// clue 1 solved
@Composable
fun SolvedPage(navController: NavController,
               TreasureViewModel: TreasureViewModel)

{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerContent(TreasureViewModel)
        Text(text = stringResource(R.string.solved))
        Spacer(Modifier.height(20.dp))
        Text(text = stringResource(R.string.clue1_info))
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            TreasureViewModel.startTimer()
            navController.navigate("second_clue")
        })
        {
            Text(text = stringResource(R.string.continue_button))
        }
    }
}