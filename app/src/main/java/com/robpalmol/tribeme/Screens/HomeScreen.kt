package com.robpalmol.tribeme.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.robpalmol.tribeme.Components.TribeElement
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Composable
fun HomeScreen(
    onItemClick: (Tribe) -> Unit,
    viewModel: MyViewModel = viewModel(),
    reloadKey: Any
) {
    val context = LocalContext.current

    // Estado reactivo que renderiza la lista
    val tribeList by viewModel.tribeData.collectAsState(initial = emptyList())
    val currentUser by viewModel.currentUser.collectAsState()

    // Efecto que dispara la llamada al backend
    LaunchedEffect(reloadKey) {
        viewModel.loadCurrentUser(context)
        viewModel.getAllTribes(context)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }

            items(tribeList) { tribe ->
                TribeElement(tribe = tribe, onClick = { onItemClick(tribe) })
            }

            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}
