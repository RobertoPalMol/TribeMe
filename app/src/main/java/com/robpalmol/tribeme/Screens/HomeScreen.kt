package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.robpalmol.tribeme.Components.TribeElement

@Composable
fun HomeScreen() {
    Column() {
        Spacer(modifier = Modifier.height(200.dp))
        TribeElement()
    }
}