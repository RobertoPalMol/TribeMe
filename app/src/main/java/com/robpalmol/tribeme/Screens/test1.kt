package com.robpalmol.tribeme.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.robpalmol.tribeme.ViewModels.MyViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.ui.theme.BluePost

@Composable
fun MyDataScreen(viewModel: MyViewModel = viewModel()) {
    val context = LocalContext.current
    val data by viewModel.userData.collectAsState()
    val error by viewModel.error.collectAsState()

    // Llamar a getAllUsers cuando la pantalla se compone por primera vez
    LaunchedEffect(Unit) {
        viewModel.getAllUsers(context)
    }

    val currentUserID = remember { mutableStateOf<Long?>(null) }

    if (error != null) {
        Text(text = "Error: $error", color = Color.Red)
    } else {
        LazyColumn {
            items(data) { user ->
                MyDataItem(user, currentUserID)
            }
        }
    }
}


// Componente MyDataItem que maneja los clics
@Composable
fun MyDataItem(user: User, currentUserID: MutableState<Long?>) {
    Card(modifier = Modifier.background(BluePost)) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    // Actualizar el ID del usuario cuando se hace clic
                    currentUserID.value = user.usuarioId
                    Log.d("PostElement", "Selected user ID: ${currentUserID.value}")
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Text(text = user.nombre)
                Text(text = user.correo)
                Log.d("MyDataItem", "User ID: ${user.usuarioId}")
            }
        }
    }
}

