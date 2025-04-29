package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robpalmol.tribeme.Components.TribeElement
import com.robpalmol.tribeme.ViewModels.MyViewModel

@Composable
fun HomeScreen(viewModel: MyViewModel = viewModel()) {
    val tribeList by viewModel.tribeData.collectAsState()
    val context = LocalContext.current

    // Llamada a la API al primer renderizado
    LaunchedEffect(Unit) {
        viewModel.getAllTribes(context)
    }

    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(tribeList) { tribe ->
            TribeElement(tribe = tribe)
        }
    }
}
