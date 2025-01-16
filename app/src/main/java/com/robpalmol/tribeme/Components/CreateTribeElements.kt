package com.robpalmol.tribeme.Components

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.GrayLetter
import com.robpalmol.tribeme.ui.theme.WhitePost
import java.time.Instant
import java.time.ZoneOffset


val category_icons = arrayOf(
    R.drawable.sports_image,
    R.drawable.music_image,
    R.drawable.art_image,
    R.drawable.social_image,
    R.drawable.familiar_image,
    R.drawable.school_image,
    R.drawable.health_image,
    R.drawable.food_image
)
val categoryNames = listOf(
    "Deportes",
    "Música",
    "Arte",
    "Social",
    "Familiar",
    "Escuela",
    "Salud",
    "Comida"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventName(name: MutableState<String>) {
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
            text = "Nombre del evento",
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
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = WhitePost,
                        focusedLabelColor = BlackPost
                    ),
                    placeholder = {
                        Text(
                            text = "Nombre del evento",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDescription(description: MutableState<String>) {
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
            text = "Descripción del evento",
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
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = WhitePost,
                        focusedLabelColor = BlackPost
                    ),
                    placeholder = {
                        Text(
                            text = "Descripción del evento",
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
fun EventDate(
    isStartDate: Boolean,
    selectedDate: MutableState<String>,
    selectedTime: MutableState<String>,
    startDate: MutableState<String>,
    endDate: MutableState<String>,
    dateError: MutableState<Boolean>
) {
    var datePicker by remember { mutableStateOf(false) }
    var timePicker by remember { mutableStateOf(false) }
    val errorMessage = "La fecha de finalización no puede ser anterior a la fecha de inicio."
    val datePickerState = rememberDatePickerState()

    Spacer(modifier = Modifier.height(30.dp))

    Row {
        if (isStartDate) {
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "Día de inicio:",
                style = TextStyle(fontSize = 15.sp)
            )
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "Hora de inicio:",
                style = TextStyle(fontSize = 15.sp)
            )
        } else {
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "Día de finalización:",
                style = TextStyle(fontSize = 15.sp)
            )
            Spacer(modifier = Modifier.width(60.dp))
            Text(
                text = "Hora de finalización:",
                style = TextStyle(fontSize = 15.sp)
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { datePicker = true },
            colors = ButtonDefaults.buttonColors(WhitePost)
        ) {
            Text(
                text = if (selectedDate.value.isNotEmpty()) selectedDate.value else "Introduce el día",
                style = TextStyle(fontSize = 15.sp, color = GrayLetter)
            )
        }

        Button(
            onClick = { timePicker = true },
            colors = ButtonDefaults.buttonColors(WhitePost)
        ) {
            Text(
                text = if (selectedTime.value.isNotEmpty()) selectedTime.value else "Introduce la hora",
                style = TextStyle(fontSize = 15.sp, color = GrayLetter)
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (dateError.value && isStartDate.equals(false)) {
            Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
        }
    }

    LaunchedEffect(selectedDate.value, startDate.value, endDate.value) {
        if (isStartDate) {
            // Validar cuando se cambia la fecha de inicio
            if (startDate.value.isNotEmpty() && endDate.value.isNotEmpty() && startDate.value > endDate.value) {
                dateError.value = true
            } else {
                dateError.value = false
                startDate.value = selectedDate.value // Guardar la fecha seleccionada como inicio
            }
        } else {
            // Validar cuando se cambia la fecha de finalización
            if (startDate.value.isNotEmpty() && endDate.value.isNotEmpty() && selectedDate.value < startDate.value) {
                dateError.value = true
            } else {
                dateError.value = false
                endDate.value = selectedDate.value // Guardar la fecha seleccionada como finalización
            }
        }
    }



    // Diálogo para seleccionar la fecha
    if (datePicker) {
        DatePickerDialog(
            onDismissRequest = { datePicker = false },
            confirmButton = {
                Button(onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    selectedMillis?.let {
                        val selectedDateFormatted = Instant.ofEpochMilli(it)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                            .toString()
                        selectedDate.value = selectedDateFormatted
                    }
                    datePicker = false
                }) {
                    Text(text = "Confirmar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Diálogo para seleccionar la hora
    if (timePicker) {
        TimePickerDialog(
            onDismissRequest = { timePicker = false },
            onTimeConfirm = { hour, minute ->
                val formattedTime = String.format("%02d:%02d", hour, minute)
                selectedTime.value = formattedTime
                timePicker = false
            }
        )
    }
}

//componente prestado de StackOverflow
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeConfirm: (hour: Int, minute: Int) -> Unit
) {

    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(onClick = { onTimeConfirm(selectedHour, selectedMinute) }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("Cancelar")
            }
        },
        title = { Text("Selecciona la hora") },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Selector de Horas
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(24) { hour ->
                        Text(
                            text = hour.toString().padStart(2, '0'),
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { selectedHour = hour },
                            color = if (hour == selectedHour) Color.Blue else Color.Black,
                            fontWeight = if (hour == selectedHour) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }

                // Separador de Hora y Minutos
                Text(
                    text = ":",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                // Selector de Minutos
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(60) { minute ->
                        Text(
                            text = minute.toString().padStart(2, '0'),
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { selectedMinute = minute },
                            color = if (minute == selectedMinute) Color.Blue else Color.Black,
                            fontWeight = if (minute == selectedMinute) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    )
}



@Composable
fun CategoriasCarrusel(
    selectedCategories: MutableState<Set<String>>, // Cambiado a Set para manejar la selección de forma más robusta.
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
                    onCategorySelect(selectedCategories.value)
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
        modifier = Modifier.clickable(onClick = onClick) // Detectar clic
    ) {
        Button(
            onClick = onClick,
            colors = if (isSelected) ButtonDefaults.buttonColors(BluePost) else ButtonDefaults.buttonColors(WhitePost),
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
fun AddPhoto(imageUrl: MutableState<String>) {
    val context = LocalContext.current

    val cropImage = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val croppedImageUri = result.uriContent

            croppedImageUri?.let { uri ->
                // Solo guardamos la URI de la imagen seleccionada y recortada
                imageUrl.value = uri.toString()
                Toast.makeText(context, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Error al recortar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            // Llama a la actividad para recortar la imagen seleccionada
            cropImage.launch(
                CropImageContractOptions(
                    uri = selectedUri,
                    cropImageOptions = CropImageOptions(
                        guidelines = CropImageView.Guidelines.ON,  // Muestra las guías de recorte(se puede desactivar)
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
                Text(text = "Agregar Foto", color = WhitePost)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUbication(ubication: MutableState<String>) {
    Column {
        Spacer(modifier = Modifier.height(30.dp))
        Row(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(60.dp))
            Text(text = "Introduce la ubicación")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Spacer(modifier = Modifier.width(50.dp))
            Box(
                modifier = Modifier
                    .background(WhitePost, shape = RoundedCornerShape(20.dp))
                    .padding(8.dp)
                    .size(280.dp, 50.dp)
            ) {
                TextField(
                    value = ubication.value,
                    onValueChange = { ubication.value = it },
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = WhitePost,
                        focusedLabelColor = BlackPost
                    ),
                    placeholder = {
                        Text(
                            text = "Ubicación",
                            style = TextStyle(color = GrayLetter)
                        )
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Spacer(modifier = Modifier.width(60.dp))
            Text(
                text = "Verifica que realmente esta es tu dirección",
                color = BluePost,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Spacer(modifier = Modifier.width(50.dp))
            Box(
                modifier = Modifier
                    .background(BlackPost, shape = RoundedCornerShape(20.dp))
                    .padding(8.dp)
                    .size(275.dp, 30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .size(275.dp, 30.dp)
                        .clickable { /*TODO*/ },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Obtener Ubicación Actual", color = WhitePost)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .size(350.dp, 400.dp)
                    .background(WhitePost, shape = RoundedCornerShape(20.dp))
                    .pointerInput(Unit) {
                        detectTapGestures { }
                    }
            ) {
                GoogleMap()
            }
        }
    }
}

@Composable
fun SaveElement(
    name: MutableState<String>,
    description: MutableState<String>,
    startDate: MutableState<String>,
    startTime: MutableState<String>,
    endDate: MutableState<String>,
    endTime: MutableState<String>,
    selectedCategories: MutableState<Set<String>>, // Este estado puede estar vacío
    ubication: MutableState<String>,
    autorName: MutableState<String>,
    autorId: MutableState<String>,
    imageUrl: MutableState<String>, // Este estado puede estar vacío
    dateError: MutableState<Boolean>
) {
    val context = LocalContext.current
    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = {
                if (!dateError.value) {
                    Log.d("SaveElement", "Botón clicado")

                    // Si hay imagen seleccionada, subimos la imagen a Firebase Storage
                    if (imageUrl.value.isNotEmpty()) {
                        // Subir la imagen a Firebase Storage antes de publicar el evento
                        val storageReference = FirebaseStorage.getInstance().reference
                        val fileName = "${System.currentTimeMillis()}.jpeg"
                        val imageRef = storageReference.child("event_images/$fileName")

                        val uri = Uri.parse(imageUrl.value)
                        imageRef.putFile(uri)
                            .addOnSuccessListener {
                                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                    // Guardamos el evento con la URL de la imagen subida
                                    saveEvent(
                                        name,
                                        description,
                                        startDate,
                                        startTime,
                                        endDate,
                                        endTime,
                                        selectedCategories,
                                        ubication,
                                        autorName,
                                        autorId,
                                        downloadUri.toString(),
                                        context
                                    )
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context,"Error al subir la imagen", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        // Si no hay imagen, guardamos el evento sin la URL de la imagen
                        saveEvent(
                            name,
                            description,
                            startDate,
                            startTime,
                            endDate,
                            endTime,
                            selectedCategories,
                            ubication,
                            autorName,
                            autorId,
                            "",
                            context
                        )
                    }
                }
            },
            modifier = Modifier
                .width(275.dp)
                .align(Alignment.CenterVertically),
            enabled = !dateError.value,
            colors = ButtonDefaults.buttonColors(containerColor = BluePost)
        ) {
            Text(
                text = "Publicar evento",
                fontSize = 24.sp,
                color = WhitePost,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
                    .drawBehind {
                        if (dateError.value) { // Dibujar línea tachada si hay error
                            val textWidth = size.width
                            val textHeight = size.height
                            drawLine(
                                color = Color.Red,
                                start = Offset(0f, textHeight / 2),
                                end = Offset(textWidth, textHeight / 2),
                                strokeWidth = 4f
                            )
                        }
                    }
            )
        }

    }
}

// Función para guardar el evento en Firestore
fun saveEvent(
    name: MutableState<String>,
    description: MutableState<String>,
    startDate: MutableState<String>,
    startTime: MutableState<String>,
    endDate: MutableState<String>,
    endTime: MutableState<String>,
    selectedCategories: MutableState<Set<String>>, // Este estado puede estar vacío
    ubication: MutableState<String>,
    autorName: MutableState<String>,
    autorId: MutableState<String>,
    imageUrl: String,// Este estado puede estar vacío
    context: Context
){
    val firestore = FirebaseFirestore.getInstance()
    Toast.makeText(context, "Publicando Evento (esta acción puede tardar unos instantes)", Toast.LENGTH_SHORT).show()
    val eventId = firestore.collection("events").document().id
    Log.d("SaveElement", "Nuevo ID del evento: $eventId")

    val event = hashMapOf(
        "id" to eventId,
        "name" to name.value,
        "description" to description.value,
        "startDate" to startDate.value,
        "startTime" to startTime.value,
        "endDate" to endDate.value,
        "endTime" to endTime.value,
        "categories" to selectedCategories.value.toList(),
        "ubication" to ubication.value,
        "author" to autorName.value,
        "authorId" to autorId.value,
        "imageUrl" to imageUrl // La URL de la imagen, que puede estar vacía
    )

    Log.d("SaveElement", "Evento a guardar: $event")

    firestore.collection("events")
        .document(eventId)
        .set(event, SetOptions.merge())
        .addOnSuccessListener {
            Log.d("SaveElement", "Evento guardado con éxito")
            Toast.makeText(context, "Evento publicado con éxito", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { exception ->
            Log.e("SaveElement", "Error al guardar el evento: ${exception.message}")
            Toast.makeText(context, "Error al publicar el evento", Toast.LENGTH_SHORT).show()
        }
}







