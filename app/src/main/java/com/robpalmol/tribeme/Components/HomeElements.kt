package com.robpalmol.tribeme.Components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.robpalmol.tribeme.DataBase.Models.EventoDTO
import com.robpalmol.tribeme.DataBase.Models.ImageUploadResponse
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.DataBase.RetrofitInstance
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground
import com.robpalmol.tribeme.ui.theme.GrayCategory
import com.robpalmol.tribeme.ui.theme.PinkPost
import com.robpalmol.tribeme.ui.theme.WhitePost
import com.robpalmol.tribeme.util.SessionManager


@Composable
fun TribeElement(
    tribe: Tribe,
    onClick: () -> Unit,
    context: Context
) {

    fun obtenerUrlImagen(imagenDb: String): String {
        val baseUrl = RetrofitInstance.BASE_URL + "/api/tribus/imagenes/"
        val fileName = imagenDb.substringAfterLast("/")
        return baseUrl + fileName
    }

    val urlImagen = obtenerUrlImagen(tribe.imagenUrl)
    val token = SessionManager(context).getToken()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(10.dp)
            .background(WhitePost, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
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
            Box(modifier = Modifier.fillMaxWidth()) {
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
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(urlImagen)
                                    .addHeader("Authorization", "Bearer $token")
                                    .crossfade(true)
                                    .listener(
                                        object : ImageRequest.Listener {
                                            override fun onError(request: ImageRequest, result: ErrorResult) {
                                                Log.e("AsyncImage", "Error al cargar la imagen", result.throwable)
                                            }
                                            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                                                Log.d("AsyncImage", "Imagen cargada correctamente")
                                            }
                                        }
                                    )
                                    .build(),
                                contentDescription = "Imagen de la tribu",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Log.d("TribeElement", "Imagen URL: $urlImagen")
                            Log.d("TribeElement", "Imagen URL original: ${tribe.imagenUrl}")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

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
            }
        }
    }
}



@Composable
fun PostCategory(category: String) {
    Row(
        modifier = Modifier
            .height(25.dp)
            .background(GrayCategory, shape = RoundedCornerShape(7.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val iconPainter = when (category) {
            "Deportes" -> painterResource(id = R.drawable.sports_image)
            "Música" -> painterResource(id = R.drawable.music_image)
            "Arte" -> painterResource(id = R.drawable.art_image)
            "Tecnología" -> painterResource(id = R.drawable.tech_screen)
            "Mascotas" -> painterResource(id = R.drawable.pet_footprint)
            "Escuela" -> painterResource(id = R.drawable.school_image)
            "Friki" -> painterResource(id = R.drawable.freak_die)
            "Estilo de vida" -> painterResource(id = R.drawable.travel_suitcase)
            else -> {
                painterResource(id = R.drawable.eye_off)
            }
        }

        Image(
            painter = iconPainter,
            contentDescription = category,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = category,
            fontSize = 12.sp,
            color = BlackPost
        )
    }
}


@Composable
fun TribeDetailScreen(tribe: Tribe, viewModel: MyViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()
    val eventos by viewModel.eventos.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(tribe.tribuId) {
        viewModel.getAllEventos(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = currentUser?.nombre ?: "usuario",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhitePost, shape = RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                // Nombre y autor
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = tribe.nombre,
                        fontSize = 22.sp,
                        fontWeight = Bold,
                        color = BlackPost
                    )
                    Text(
                        text = "@${tribe.autorNombre}",
                        color = BluePost,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = tribe.ubicacion,
                    color = BlackPost,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Imagen o placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(BlackPost)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción
                Text(
                    text = tribe.descripcion,
                    fontSize = 14.sp,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = BlackPost
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Participantes
                Text(
                    text = "Participantes: ${tribe.miembros?.size}/${tribe.numeroMaximoMiembros}",
                    fontSize = 14.sp,
                    color = BlackPost
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Categorías
                Text(
                    text = "Categorías",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = BlackPost
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(tribe.categorias) { category ->
                        PostCategory(category = category)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                        .background(color = GrayCategory, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .background(PinkPost, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(text = "Eventos programados:", fontWeight = Bold, color = BlackPost)
                    }

                    // Tablón de eventos
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(eventos) { evento ->
                            Log.d("TribeDetailScreen", "Evento: $evento")
                            TribeEventDetails(evento = evento)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                        .background(color = GrayCategory, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = "Lista de participantes:", fontWeight = Bold, color = BlackPost)

                    Spacer(modifier = Modifier.height(8.dp))

                    //Lista de participantes de la tribu
                    LazyColumn ( modifier = Modifier.height(300.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)) {

                        Log.d("TribeDetailScreen", "Participantes: ${tribe.miembros}")

                        items(tribe.miembros.orEmpty()) { miembro ->
                            Log.d("TribeDetailScreen", "Participantes: $miembro")
                            TribeMemberItem(user = miembro, tribe = tribe)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row (Modifier.align(alignment = Alignment.CenterHorizontally)) {
                        //boton de unirse a la tribu
                        currentUser?.let { user ->
                            val yaEsMiembro = tribe.miembros.any { it.usuarioId == user.usuarioId }

                            Log.d("DEBUG", "Miembros tribu: ${tribe.miembros.map { it.usuarioId }}")
                            Log.d("DEBUG", "Usuario actual ID: ${user.usuarioId}")
                            Log.d("DEBUG", "Ya es miembro? $yaEsMiembro")

                            if (!yaEsMiembro) {
                                UnirseTribuButton(
                                    context = context,
                                    viewModel = viewModel,
                                    tribu = tribe,
                                    usuarioActual = user,
                                    miembros = tribe.miembros
                                )
                            } else {
                                SalirDeTribuButton(
                                    context = context,
                                    viewModel = viewModel,
                                    tribu = tribe,
                                    usuarioActual = user
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TribeEventDetails(evento: EventoDTO) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WhitePost, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = evento.nombre, color = BlackPost)
        Text(text = "Hora: ${evento.hora}", color = BlackPost)
        Text(text = "Lugar: ${evento.lugar}", color = BlackPost)
    }
}

@Composable
fun TribeMemberItem(user: User, tribe: Tribe) {
    val isAuthor = user.usuarioId.toString() == tribe.autorId

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // AsyncImage(model = user.avatarUrl, contentDescription = null)
                Text(
                    text = user.nombre.firstOrNull()?.uppercase() ?: "",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = user.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )

                if (isAuthor) {
                    Text(
                        text = "Creador de la tribu",
                        fontSize = 14.sp,
                        color = Color(0xFF00796B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun UnirseTribuButton(
    context: Context,
    viewModel: MyViewModel,
    tribu: Tribe,
    usuarioActual: User,
    miembros: List<User>
) {
    Button(onClick = {
        viewModel.unirseATribu(
            context = context,
            tribuId = tribu.tribuId,
            usuarioId = usuarioActual.usuarioId,
            miembros = miembros,
            onError = { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        )
    }) {
        Text(text = "Unirse a la tribu")
    }
}

@Composable
fun SalirDeTribuButton(
    context: Context,
    viewModel: MyViewModel,
    tribu: Tribe,
    usuarioActual: User
) {
    Button(onClick = {
        viewModel.salirDeTribu(
            context = context,
            tribuId = tribu.tribuId,
            usuarioId = usuarioActual.usuarioId,
            onError = { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        )
    }) {
        Text(text = "Salir de la tribu")
    }
}


