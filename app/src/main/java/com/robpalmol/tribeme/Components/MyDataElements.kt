package com.robpalmol.tribeme.Components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.robpalmol.tribeme.DataBase.Models.EventoDTO
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground
import com.robpalmol.tribeme.ui.theme.GrayCategory
import com.robpalmol.tribeme.ui.theme.PinkPost
import com.robpalmol.tribeme.ui.theme.WhitePost
import com.robpalmol.tribeme.util.SessionManager


@Composable
fun TribeElementEvent(
    tribe: Tribe,
    onClick: () -> Unit,
    onClickEvento: (EventoDTO) -> Unit,
    context: Context
) {
    val viewModel: MyViewModel = viewModel()
    val token = SessionManager(context).getToken()
    val urlImagen = viewModel.obtenerUrlImagen(tribe.imagenUrl)

    val imageLoader = remember {
        viewModel.createAuthenticatedImageLoader(context, token.toString())
    }

    var showEvents by remember { mutableStateOf(false) }
    val eventosDeLaTribu = viewModel.eventosPorTribu[tribe.tribuId] ?: emptyList()
    LaunchedEffect(tribe.tribuId) {
        viewModel.getEventosPorTribu(context, tribe.tribuId)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .background(WhitePost, shape = RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.width(25.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    tribe.nombre,
                    style = TextStyle(fontWeight = Bold),
                    fontSize = 24.sp
                )
                Text(
                    text = "@${tribe.autorNombre}",
                    color = BluePost,
                    modifier = Modifier.align(Alignment.TopEnd),
                    fontSize = 24.sp
                )
            }

            Text(
                text = tribe.ubicacion,
                color = BlackPost,
                fontSize = 16.sp
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(BlackPost, shape = RoundedCornerShape(20.dp))
                                .padding(10.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(urlImagen)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Imagen de la tribu",
                                imageLoader = imageLoader,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(0.7f))

                        Text("Descripción:", style = TextStyle(fontWeight = Bold))
                        Text(
                            text = tribe.descripcion,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(text = "Participantes: ${tribe.miembros.size}/${tribe.numeroMaximoMiembros}")

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Categorías:",
                            style = TextStyle(fontWeight = Bold)
                        )

                        Spacer(modifier = Modifier.weight(0.5f))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(tribe.categorias) { category ->
                                PostCategory(category = category)
                            }
                        }
                    }
                }

                Button(
                    onClick = { showEvents = !showEvents },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = if (showEvents) "Ocultar eventos" else "Mostrar eventos")
                }

                if (showEvents) {
                    if (eventosDeLaTribu.isEmpty()) {
                        Text("No hay eventos programados para esta tribu")
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .then(Modifier.heightIn(max = 400.dp))

                        ) {
                            items(eventosDeLaTribu) { evento ->
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TribeEventDetailsExtended(
                                        evento = evento,
                                        onClick = { onClickEvento(evento) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TribeEventDetailsExtended(evento: EventoDTO, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrayCategory, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = evento.nombre, color = BlackPost, style = TextStyle(fontWeight = Bold))
        Text(text = evento.descripcion, color = BlackPost)
        Text(text = "Hora: ${evento.hora}", color = BlackPost)
        Text(text = "Lugar: ${evento.lugar}", color = BlackPost)
    }
}

@Composable
fun EventExtended(evento: EventoDTO, viewModel: MyViewModel) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsState()
    val miembros = evento.miembrosEvento ?: emptyList()

    LaunchedEffect(evento.eventoId) {
        if (viewModel.eventoSeleccionado.value?.eventoId != evento.eventoId) {
            viewModel.getEventById(context, evento.eventoId)
        }
        viewModel.loadCurrentUser(context)
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
                    fontWeight = FontWeight.Bold
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

        Spacer(modifier = Modifier.height(24.dp))

        // Contenido Evento
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Evento Programado",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(PinkPost, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WhitePost),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = evento.nombre,
                        fontSize = 20.sp,
                        fontWeight = Bold,
                        color = BlackPost
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = evento.descripcion, color = BlackPost)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "🕒 Hora: ${evento.hora}", color = BlackPost)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "📍 Lugar: ${evento.lugar}", color = BlackPost)
                    Spacer(modifier = Modifier.height(16.dp))

                    currentUser?.let { user ->
                        val yaEsMiembro = miembros.any { it.usuarioId == user.usuarioId }
                        if (yaEsMiembro) {
                            SalirDeEventoButton(context, viewModel, evento, user)
                        } else {
                            UnirseEventoButton(context, viewModel, evento, user)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Participantes
            Text(
                text = "Participantes apuntados",
                fontSize = 18.sp,
                fontWeight = Bold,
                color = BlackPost
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (miembros.isEmpty()) {
                Text(
                    text = "Aún no hay participantes.",
                    color = Color.DarkGray,
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            GrayCategory.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    miembros.forEach { miembro ->
                        Text(
                            text = "• @${miembro.nombre}",
                            color = BluePost,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Composable
fun SalirDeEventoButton(
    context: Context,
    viewModel: MyViewModel,
    evento: EventoDTO,
    usuarioActual: User
) {
    var isLoading by remember { mutableStateOf(false) }

    Button(onClick = {
        if (!isLoading) {
            isLoading = true
            viewModel.salirDeEvento(
                context = context,
                eventoId = evento.eventoId,
                usuarioId = usuarioActual.usuarioId
            )
        }
    }, enabled = !isLoading) {
        Text("Salir del evento")
    }
}


@Composable
fun UnirseEventoButton(
    context: Context,
    viewModel: MyViewModel,
    evento: EventoDTO,
    usuarioActual: User
) {
    var isLoading by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (!isLoading) {
                isLoading = true
                viewModel.unirseAEvento(
                    context = context,
                    eventoId = evento.eventoId,
                    usuarioId = usuarioActual.usuarioId
                )
            }
        },
        enabled = !isLoading
    ) {
        Text("Confirmar asistencia")
    }
}

