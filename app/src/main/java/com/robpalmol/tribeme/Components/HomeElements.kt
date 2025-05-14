package com.robpalmol.tribeme.Components

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground
import com.robpalmol.tribeme.ui.theme.GrayCategory
import com.robpalmol.tribeme.ui.theme.PinkPost
import com.robpalmol.tribeme.ui.theme.WhitePost


@Composable
fun TribeElement(tribe: Tribe, onClick: () -> Unit) {
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
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    tribe.nombre,
                    style = TextStyle(fontWeight = Bold)
                )
                Text(
                    text = "@${tribe.nombre}",
                    color = BluePost,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                )
            }
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
                        )
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

                        Text(text = "Participantes: ?/${tribe.numeroMaximoMiembros}")

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

        // Mostrar el texto de la categoría
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

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                        fontWeight = FontWeight.Bold,
                        color = BlackPost
                    )
                    Text(
                        text = "@${tribe.autorNombre}",
                        color = BluePost,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                    text = "Participantes: ?/${tribe.numeroMaximoMiembros}",
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

                // Tablón de eventos
                TribeEventDetails()
            }
        }
    }
}


@Composable
fun TribeEventDetails() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(GrayCategory),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tablón de anuncios",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = WhitePost
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                .background(color = WhitePost, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .background(PinkPost, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Eventos Programados",
                    fontWeight = FontWeight.Medium,
                    color = WhitePost
                )
            }
        }
    }
}
