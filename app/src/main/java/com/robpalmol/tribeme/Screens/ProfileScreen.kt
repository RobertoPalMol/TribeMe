package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.robpalmol.tribeme.Components.ActiveElements
import com.robpalmol.tribeme.Components.CloseSesion
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MyViewModel
) {
    // Obtener tribus y usuario actual desde el ViewModel
    val currentUser = viewModel.currentUser.collectAsState().value
    val tribes = viewModel.tribeData.collectAsState().value

    // Filtrar las tribus creadas por el usuario actual
    val userTribes = tribes.filter { it.autorId == currentUser?.usuarioId.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // AsyncImage(model = user.avatarUrl, contentDescription = null)
                Text(
                    text = currentUser?.nombre?.firstOrNull()?.uppercase() ?: "",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = Bold
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = currentUser?.nombre ?: "Bienvenido",
                color = Color.White,
                fontSize = 32.sp
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                ActiveElements()
                Spacer(modifier = Modifier.height(5.dp))


                // Mostrar las tribus creadas por el usuario
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(userTribes) { tribe ->
                        // Aquí puedes personalizar la forma en que se muestran las tribus
                        Text(
                            text = tribe.nombre,
                            color = Color.White,
                            style = TextStyle(fontSize = 24.sp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    item {
                        Spacer(modifier = Modifier.height(60.dp))
                    }

                    // Botón para cerrar sesión
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        CloseSesion(navController = navController)
                        Spacer(modifier = Modifier.height(120.dp))
                    }
                }
            }
        }
    }
}