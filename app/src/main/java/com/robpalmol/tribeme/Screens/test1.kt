package com.robpalmol.tribeme.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Scaffold
import com.robpalmol.tribeme.VIewModels.MyViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.ui.theme.BluePost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDataScreen(viewModel: MyViewModel = viewModel()) {
    val data by viewModel.data.collectAsState() // Observa los datos del ViewModel
    // Estado para manejar el ID del usuario seleccionado
    val currentUserID = remember { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Data") }) },
        content = {
            Column {
                Spacer(Modifier.height(100.dp))
                LazyColumn {
                    items(data) { item -> // Iterar sobre la lista de usuarios
                        MyDataItem(item, currentUserID) // Pasar el usuario y el estado
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Agrega lógica para añadir datos */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Data")
            }
        }
    )
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
                Text(text = user.contraseña)
                Text(text = user.fechaCreacion)
                Log.d("MyDataItem", "User ID: ${user.usuarioId}")
            }
        }
    }
}
