package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.background
import com.robpalmol.tribeme.ViewModels.MyViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robpalmol.tribeme.Components.TribeElementEvent
import com.robpalmol.tribeme.DataBase.Models.EventoDTO
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground


@Composable
fun MyDataScreen(
    viewModel: MyViewModel = viewModel(),
    onItemClick: (Tribe) -> Unit,
    onEventoClick: (EventoDTO) -> Unit
) {
    val context = LocalContext.current
    val data by viewModel.tribeData.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCurrentUser(context)
        viewModel.getMyTribes(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Encabezado con avatar y nombre
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentUser?.nombre?.firstOrNull()?.uppercase() ?: "",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = currentUser?.nombre ?: "Usuario",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (error != null) {
            Text(
                text = "Error: $error",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            // Contenedor visual con esquinas redondeadas y fondo degradado
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(Brush.verticalGradient(DifuminatedBackground))
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(top = 20.dp, bottom = 120.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(data) { tribe ->
                            TribeElementEvent(
                                tribe = tribe,
                                onClick = { onItemClick(tribe) },
                                onClickEvento = { onEventoClick(it) },
                                context = context
                            )
                        }
                    }
                }
            }
        }
    }
}
