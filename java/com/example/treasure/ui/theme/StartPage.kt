/*
Stephan Gonzales / gonzstep@oregonstate.edu
CS 492 / Oregon state University

 */
package com.example.treasure.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.treasure.R


// Start page for title, rules, start game button
@Composable
fun StartPage(navController: NavController,
              viewModel: TreasureViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.title))
        Spacer(Modifier.height(20.dp))
        Text(text = stringResource(R.string.rules))
        Text(text = stringResource(R.string.rule1))
        Text(text = stringResource(R.string.rule2))
        Text(text = stringResource(R.string.rule3))
        Text(text = stringResource(R.string.rule4))
        Text(text = stringResource(R.string.rule5))
        Text(text = stringResource(R.string.rule6))
        Spacer(Modifier.height(30.dp))
        Button(onClick = {viewModel.startTimer()
                         navController.navigate("first_clue")}) {
            Text(text = stringResource(R.string.start_button))
        }
    }
}