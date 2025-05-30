package com.robpalmol.tribeme.Components

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.TribuUpdateDTO
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground
import com.robpalmol.tribeme.ui.theme.WhitePost



@Composable
fun YourPostElement(
    tribe: Tribe,
    navController: NavController,
    viewModel: MyViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(350.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(WhitePost)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tribe.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = BlackPost
            )
            Text(
                text = "@${tribe.autorNombre}",
                fontSize = 14.sp,
                color = BluePost
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate("Editar/${tribe.tribuId}")
                },
                colors = ButtonDefaults.buttonColors(BluePost),
                modifier = Modifier.height(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.pen_edit_publication),
                    contentDescription = "Editar Icon",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Editar publicación", fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(BluePost),
                modifier = Modifier.height(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.bin_edit_publication),
                    contentDescription = "Eliminar Icon",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Eliminar publicación", fontSize = 10.sp)
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {
            DeletePopup(
                tribuId = tribe.tribuId,
                viewModel = viewModel,
                navController = navController,
                onDismiss = { showDialog = false }
            )
        }
    }
}


@Preview
@Composable
fun ActiveElements() {
    Box {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .height(60.dp)
                .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
                .background(BlackPost)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Tus tribus activas",
                fontSize = 22.sp,
                color = WhitePost
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .height(60.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = painterResource(R.drawable.down_arrow),
                contentDescription = "",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun DeletePopup(
    tribuId: Long,
    viewModel: MyViewModel,
    navController: NavController,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .width(220.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BlackPost)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(WhitePost)
                .padding(10.dp)
        ) {
            Text(
                text = "¿Seguro que deseas eliminar la tribu?",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        viewModel.eliminarTribu(
                            tribuId = tribuId,
                            onSuccess = {
                                Toast.makeText(context, "Tribu eliminada", Toast.LENGTH_SHORT).show()
                                navController.navigate("Inicio")
                            },
                            onError = { errorMsg ->
                                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                            },
                            context = context
                        )
                    },
                    colors = ButtonDefaults.buttonColors(BluePost)
                ) {
                    Text("Sí")
                }

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(BluePost)
                ) {
                    Text("No")
                }
            }
        }
    }
}



@Composable
fun CloseSesion(
    navController: NavController,
){
    Button(
        onClick = {
            navController.navigate("Login") {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
        },
        colors = ButtonDefaults.buttonColors(BluePost),
        modifier = Modifier.width(200.dp).height(50.dp)
    ) {
        Text(
            text = "Cerrar sesión",
            color = WhitePost,
            fontSize = 16.sp
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTribe(
    title: String,
    viewModel: MyViewModel,
    tribeData: Tribe
) {
    val name = rememberSaveable { mutableStateOf(tribeData.nombre) }
    val description = rememberSaveable { mutableStateOf(tribeData.descripcion) }
    val members = rememberSaveable { mutableStateOf(tribeData.numeroMaximoMiembros) }
    val selectedCategories = rememberSaveable { mutableStateOf(tribeData.categorias) }
    val private1 = remember { mutableStateOf(tribeData.esPrivada) }
    val ubicacion = rememberSaveable { mutableStateOf(tribeData.ubicacion) }

    val selectedImageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val uploadedImageUrl = rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                color = Color.White,
                style = TextStyle(fontSize = 32.sp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
        ) {
            item { TribeName(name) }
            item { TribeDescription(description = description) }
            item { Ubicacion(ubicacion) }
            item { MaxMembers(members = members) }
            item {
                CategoriasCarrusel(
                    selectedCategories = selectedCategories,
                    onCategorySelect = { updatedCategories ->
                        selectedCategories.value = updatedCategories.toMutableList()
                    }
                )
            }

            item {
                AddPhoto(
                    selectedImageUri = selectedImageUri,
                    onImageSelected = { uri -> selectedImageUri.value = uri }
                )
            }

            item {
                PostPhoto(
                    imageUri = selectedImageUri.value,
                    imageUrl = uploadedImageUrl.value
                )
            }

            item { BooleanTribe("Tribu privada", private1) }
            item { BooleanTribe("Los miembros podrán crear eventos", remember { mutableStateOf(true) }) }

            item {
                UpdateElement(
                    name = name,
                    description = description,
                    selectedImageUri = selectedImageUri,
                    uploadedImageUrl = uploadedImageUrl,
                    selectedCategories = selectedCategories,
                    private = private1,
                    members = members,
                    context = context,
                    viewModel = viewModel,
                    ubicacion = ubicacion,
                    tribeId = tribeData.tribuId
                )
            }

            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
fun UpdateElement(
    name: MutableState<String>,
    description: MutableState<String>,
    selectedImageUri: MutableState<Uri?>,
    uploadedImageUrl: MutableState<String?>,
    selectedCategories: MutableState<List<String>>,
    private: MutableState<Boolean>,
    members: MutableState<Int>,
    context: Context,
    viewModel: MyViewModel,
    ubicacion: MutableState<String>,
    tribeId: Long
) {
    Button(
        onClick = {
            if (name.value.isBlank() || description.value.isBlank()) {
                Toast.makeText(context, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@Button
            }

            // Si se ha seleccionado una nueva imagen, súbela
            if (selectedImageUri.value != null) {
                viewModel.subirImagen(selectedImageUri.value!!, context) { url ->
                    uploadedImageUrl.value = url

                    // Una vez subida, continuar con el update
                    val request = TribuUpdateDTO(
                        tribuId = tribeId,
                        nombre = name.value,
                        descripcion = description.value,
                        categorias = selectedCategories.value,
                        imagenUrl = uploadedImageUrl.value ?: "",
                        numeroMaximoMiembros = members.value,
                        esPrivada = private.value,
                        ubicacion = ubicacion.value,
                    )

                    viewModel.updateTribe(tribeId, request, context)
                    Toast.makeText(context, "Tribu actualizada", Toast.LENGTH_SHORT).show()
                }
            } else {

                val request = TribuUpdateDTO(
                    tribuId = tribeId,
                    nombre = name.value,
                    descripcion = description.value,
                    categorias = selectedCategories.value,
                    imagenUrl = uploadedImageUrl.value ?: "",
                    numeroMaximoMiembros = members.value,
                    esPrivada = private.value,
                    ubicacion = ubicacion.value,
                )

                viewModel.updateTribe(tribeId, request, context)
                Toast.makeText(context, "Tribu actualizada", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Guardar cambios")
    }
}
