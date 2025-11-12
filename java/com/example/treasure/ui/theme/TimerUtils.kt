/*
Stephan Gonzales / gonzstep@oregonstate.edu
CS 492 / Oregon state University

 */
package com.example.treasure.ui.theme

fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}