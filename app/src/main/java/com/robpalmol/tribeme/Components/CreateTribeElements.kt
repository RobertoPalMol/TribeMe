package com.robpalmol.tribeme.Components

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.robpalmol.tribeme.DataBase.Models.TribuDTO
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.GrayLetter
import com.robpalmol.tribeme.ui.theme.WhitePost


val category_icons = arrayOf(
    R.drawable.sports_image,
    R.drawable.music_image,
    R.drawable.art_image,
    R.drawable.tech_screen,
    R.drawable.pet_footprint,
    R.drawable.school_image,
    R.drawable.freak_die,
    R.drawable.travel_suitcase
)
val categoryNames = listOf(
    "Deportes",
    "Música",
    "Arte",
    "Tecnología",
    "Mascotas",
    "Escuela",
    "Friki",
    "Estilo de vida"
)

val imagenState = mutableStateOf<Boolean?>(false)

@Composable
fun TribeName(name: MutableState<String>) {
    val maxChar = 20
    Spacer(
        modifier = Modifier
            .height(30.dp)
    )
    Row {
        Spacer(
            modifier = Modifier
                .width(60.dp)
        )
        Text(
            text = "Nombre de la Tribu",
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
    Spacer(
        modifier = Modifier
            .height(10.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(WhitePost, shape = RoundedCornerShape(20.dp))
                .padding(8.dp)
                .size(300.dp, 75.dp)
        ) {
            Row {
                TextField(
                    value = name.value,
                    onValueChange = { if (it.length <= maxChar) name.value = it },
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.colors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = BlackPost,
                        disabledContainerColor = WhitePost,
                        unfocusedContainerColor = WhitePost,
                        focusedContainerColor = WhitePost
                    ),
                    placeholder = {
                        Text(
                            text = "Nombre de la Tribu",
                            style = TextStyle(color = GrayLetter)
                        )
                    },
                    modifier = Modifier
                        .height(75.dp),
                    supportingText = {
                        Text(
                            text = "${name.value.length} / $maxChar",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TribeDescription(description: MutableState<String>) {
    val maxChar = 200
    Spacer(
        modifier = Modifier
            .height(30.dp)
    )
    Row {
        Spacer(
            modifier = Modifier
                .width(60.dp)
        )
        Text(
            text = "Descripción de la Tribu",
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
    Spacer(
        modifier = Modifier
            .height(10.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(WhitePost, shape = RoundedCornerShape(20.dp))
                .padding(8.dp)
                .size(300.dp, 150.dp)
        ) {
            Row {
                TextField(
                    value = description.value,
                    onValueChange = { if (it.length <= maxChar) description.value = it },
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.colors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = BlackPost,
                        focusedContainerColor = WhitePost,
                        unfocusedContainerColor = WhitePost,
                        disabledContainerColor = WhitePost
                    ),
                    placeholder = {
                        Text(
                            text = "Descripción de la Tribu",
                            style = TextStyle(color = GrayLetter)
                        )
                    },
                    modifier = Modifier
                        .height(150.dp),
                    supportingText = {
                        Text(
                            text = "${description.value.length} / $maxChar",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                        )
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxMembers(members: MutableState<Int>) {
    val expanded = remember { mutableStateOf(false) }
    val options = (1..25).toList()

    Spacer(modifier = Modifier.height(24.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1C1C1E))
            .padding(16.dp)
    ) {
        Text(
            text = "Número máximo de miembros",
            style = TextStyle(fontSize = 16.sp, color = Color.White),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value }
        ) {
            TextField(
                readOnly = true,
                value = members.value.toString(),
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = BlackPost,
                    unfocusedTextColor = BlackPost,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .heightIn(max = 250.dp)
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = selectionOption.toString(),
                                color = Color.Black
                            )
                        },
                        onClick = {
                            members.value = selectionOption
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}


@Composable
fun CategoriasCarrusel(
    selectedCategories: MutableState<List<String>>,
    onCategorySelect: (Set<String>) -> Unit
) {
    Spacer(modifier = Modifier.height(30.dp))
    Row {
        Spacer(modifier = Modifier.width(60.dp))
        Text(
            text = "Categorías (Opcional)",
            style = TextStyle(fontSize = 15.sp)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))

    LazyRow {
        items(category_icons.size) { index ->
            if (index == 0) {
                Spacer(modifier = Modifier.width(15.dp))
            }

            val categoryName = categoryNames[index]
            val isSelected = categoryName in selectedCategories.value

            CategoryButton(
                icon = category_icons[index],
                isSelected = isSelected,
                onClick = {
                    // Actualizar el estado local al hacer clic
                    selectedCategories.value = if (isSelected) {
                        selectedCategories.value - categoryName
                    } else {
                        selectedCategories.value + categoryName
                    }
                    // Notificar al padre sobre el cambio
                    onCategorySelect(selectedCategories.value.toSet())
                },
                name = categoryName
            )

            if (index < category_icons.size) {
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
}

@Composable
fun CategoryButton(
    icon: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    name: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Button(
            onClick = onClick,
            colors = if (isSelected) ButtonDefaults.buttonColors(BluePost) else ButtonDefaults.buttonColors(
                WhitePost
            ),
            modifier = Modifier
                .height(45.dp)
                .clip(RoundedCornerShape(20.dp, 20.dp)),
            interactionSource = interactionSource
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Text(name, color = if (isSelected) WhitePost else GrayLetter)
            }
        }
        Box(
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
        ) {
            Icon(
                painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .height(45.dp)
                    .width(45.dp)
                    .absoluteOffset(y = (-5).dp),
                tint = if (isSelected) WhitePost else BluePost
            )
        }
    }
}

@Composable
fun AddPhoto(
    selectedImageUri: MutableState<Uri?>,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current

    val cropImage = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val croppedImageUri = result.uriContent
            croppedImageUri?.let { uri ->
                selectedImageUri.value = uri
                onImageSelected(uri)
            }
        } else {
            Toast.makeText(context, "Error al recortar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            cropImage.launch(
                CropImageContractOptions(
                    uri = selectedUri,
                    cropImageOptions = CropImageOptions(
                        guidelines = CropImageView.Guidelines.ON,
                        aspectRatioX = 1,
                        aspectRatioY = 1,
                        fixAspectRatio = true
                    )
                )
            )
        }
    }

    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { pickImageLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(BluePost),
                modifier = Modifier.height(60.dp)
            ) {
                Text(text = "+ Agregar Foto", color = WhitePost)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}


@Composable
fun PostPhoto(imageUri: Uri?, imageUrl: String?) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BlackPost)
        ) {
            when {
                imageUri != null -> {
                    // Imagen local aún no subida
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                !imageUrl.isNullOrEmpty() -> {
                    // Imagen cargada desde el backend
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = "Imagen subida",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentScale = ContentScale.Crop
                    )

                    if (painter.state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = WhitePost
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun BooleanTribe(text: String, crearEventos: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
                .background(WhitePost, shape = RoundedCornerShape(50.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.width(20.dp))
            Text(
                text = text,
                maxLines = 3,
                softWrap = true,
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )

            // Switch personalizado
            val switchWidth = 150.dp
            val switchHeight = 50.dp
            val thumbWidth = 100.dp

            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(switchWidth)
                    .height(switchHeight)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black)
                    .clickable { crearEventos.value = !crearEventos.value }
            ) {
                val alignment by animateDpAsState(
                    targetValue = if (crearEventos.value) (switchWidth - thumbWidth) else 0.dp,
                    label = "Thumb position"
                )
                Log.d("MyViewModel11", "TribuDTO: ${crearEventos.value}")
                Box(
                    modifier = Modifier
                        .offset(x = alignment)
                        .width(thumbWidth)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                        .background(BluePost),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (crearEventos.value) "Sí" else "No",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Ubicacion(ubicacion: MutableState<String>) {
    val maxChar = 30
    Spacer(
        modifier = Modifier
            .height(30.dp)
    )
    Row {
        Spacer(
            modifier = Modifier
                .width(60.dp)
        )
        Text(
            text = "Ubicación de la tribu",
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
    Spacer(
        modifier = Modifier
            .height(10.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(WhitePost, shape = RoundedCornerShape(20.dp))
                .padding(8.dp)
                .size(300.dp, 75.dp)
        ) {
            Row {
                TextField(
                    value = ubicacion.value,
                    onValueChange = { if (it.length <= maxChar) ubicacion.value = it },
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.colors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = BlackPost,
                        disabledContainerColor = WhitePost,
                        unfocusedContainerColor = WhitePost,
                        focusedContainerColor = WhitePost
                    ),
                    placeholder = {
                        Text(
                            text = "Ubicación de la Tribu",
                            style = TextStyle(color = GrayLetter)
                        )
                    },
                    modifier = Modifier
                        .height(75.dp),
                    supportingText = {
                        Text(
                            text = "${ubicacion.value.length} / $maxChar",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SaveElement(
    name: MutableState<String>,
    description: MutableState<String>,
    selectedCategories: MutableState<List<String>>,
    private: MutableState<Boolean>,
    members: MutableState<Int>,
    dateError: MutableState<Boolean>,
    context: Context,
    viewModel: MyViewModel,
    ubicacion: MutableState<String>,
    crearEventos: MutableState<Boolean>,
    selectedImageUri: MutableState<Uri?>,
    uploadedImageUrl: MutableState<String?>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (name.value.isBlank() || description.value.isBlank() || members.value <= 0) {
                    dateError.value = true
                    return@Button
                }

                val uri = selectedImageUri.value

                if (uri != null) {
                    viewModel.subirImagen(uri, context) { remoteUrl ->
                        if (remoteUrl != null) {
                            uploadedImageUrl.value = remoteUrl

                            val createRequest = TribuDTO(
                                nombre = name.value,
                                descripcion = description.value,
                                categorias = selectedCategories.value,
                                imagenUrl = remoteUrl,
                                numeroMaximoMiembros = members.value.takeIf { it > 0 } ?: 10,
                                esPrivada = private.value,
                                ubicacion = ubicacion.value,
                                autorId = viewModel.currentUser.value?.usuarioId ?: 0,
                                crearEventos = crearEventos.value
                            )

                            viewModel.createTribe(
                                context = context,
                                createRequest = createRequest,
                                onSuccess = {
                                    Toast.makeText(context, "Tribu creada", Toast.LENGTH_SHORT).show()
                                    Log.d("SaveElement", "Tribu creada con éxito")
                                },
                                onError = {
                                    Toast.makeText(context, "Error al crear la Tribu", Toast.LENGTH_SHORT).show()
                                    Log.e("SaveElement", "Error al crear tribu: $it")
                                }
                            )
                        } else {
                            Toast.makeText(context, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                            Log.e("SaveElement", "Error al subir la imagen")
                        }
                    }
                } else {
                    // No hay imagen para subir
                    val createRequest = TribuDTO(
                        nombre = name.value,
                        descripcion = description.value,
                        categorias = selectedCategories.value,
                        imagenUrl = "",
                        numeroMaximoMiembros = members.value.takeIf { it > 0 } ?: 10,
                        esPrivada = private.value,
                        ubicacion = ubicacion.value,
                        autorId = viewModel.currentUser.value?.usuarioId ?: 0,
                        crearEventos = crearEventos.value
                    )

                    viewModel.createTribe(
                        context = context,
                        createRequest = createRequest,
                        onSuccess = {
                            Toast.makeText(context, "Tribu creada", Toast.LENGTH_SHORT).show()
                            Log.d("SaveElement", "Tribu creada con éxito")
                        },
                        onError = {
                            Toast.makeText(context, "Error al crear la Tribu", Toast.LENGTH_SHORT).show()
                            Log.e("SaveElement", "Error al crear tribu: $it")
                        }
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = BluePost),
            modifier = Modifier.height(60.dp)
        ) {
            Text("Crear Tribu", color = Color.White)
        }
    }
}

