package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.background
import com.robpalmol.tribeme.ViewModels.MyViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentUser?.nombre?.firstOrNull()?.uppercase() ?: "",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = Bold
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = currentUser?.nombre ?: "Usuario",
                color = Color.White,
                fontSize = 32.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (error != null) {
            Text(text = "Error: $error", color = Color.Red)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.verticalGradient(DifuminatedBackground))
            ) {
                item { Spacer(modifier = Modifier.height(20.dp)) }

                items(data) { tribe ->
                    TribeElementEvent(
                        tribe = tribe,
                        onClick = { onItemClick(tribe) },
                        onClickEvento = { onEventoClick(it) },
                        context,
                    )
                }

                item { Spacer(modifier = Modifier.height(120.dp)) }
            }
        }
    }
}

