package com.example.fullstacktry.ui.screen.edit

import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp


@Composable
fun EditScreen(id:Int) {
    Text(text = id.toString(), fontSize = 32.sp)
}