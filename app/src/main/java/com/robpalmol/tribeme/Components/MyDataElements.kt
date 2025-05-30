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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
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
                                if (evento == null) {
                                    Log.d("Evento", evento.toString())
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text("No hay eventos programados para esta tribu")
                                } else {
                                    Log.d("Evento", "No nulo: " + evento.toString())
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

    LaunchedEffect(evento.eventoId) {
        viewModel.getEventById(context, evento.eventoId)
        viewModel.loadCurrentUser(context)
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

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .padding(25.dp)
                    .background(PinkPost, shape = RoundedCornerShape(20.dp))
            ) {
                Text(
                    "Evento Programado",
                    style = TextStyle(fontWeight = Bold),
                    fontSize = 24.sp
                )
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

                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = evento.nombre,
                            color = BlackPost,
                            style = TextStyle(fontWeight = Bold),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = evento.descripcion, color = BlackPost)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Hora: ${evento.hora}", color = BlackPost)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Lugar: ${evento.lugar}", color = BlackPost)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            currentUser?.let { user ->
                                val yaEsMiembro =
                                    evento.miembros.any { it.usuarioId == user.usuarioId }

                                when {
                                    yaEsMiembro ->{
                                        SalirDeEventoButton(
                                            context = context,
                                            viewModel = viewModel,
                                            evento = evento,
                                            usuarioActual = user
                                        )
                                    }

                                    !yaEsMiembro -> {

                                        UnirseEventoButton(
                                            context = context,
                                            viewModel = viewModel,
                                            evento = evento,
                                            usuarioActual = user,
                                            miembros = evento.miembros
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
}

@Composable
fun SalirDeEventoButton(
    context: Context,
    viewModel: MyViewModel,
    evento: EventoDTO,
    usuarioActual: User
) {

}

@Composable
fun UnirseEventoButton(
    context: Context,
    viewModel: MyViewModel,
    evento: EventoDTO,
    usuarioActual: User,
    miembros: List<User>
) {

}