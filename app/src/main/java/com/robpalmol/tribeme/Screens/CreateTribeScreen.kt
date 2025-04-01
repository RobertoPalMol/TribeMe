package com.robpalmol.tribeme.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robpalmol.tribeme.Components.AddPhoto
import com.robpalmol.tribeme.Components.AddUbication
import com.robpalmol.tribeme.Components.CategoriasCarrusel
import com.robpalmol.tribeme.Components.EventDate
import com.robpalmol.tribeme.Components.EventDescription
import com.robpalmol.tribeme.Components.EventName
import com.robpalmol.tribeme.Components.SaveElement
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTribe(Title: String, authorName: MutableState<String>, autorId: MutableState<String>) {
    val Name = rememberSaveable { mutableStateOf("") }
    val Description = rememberSaveable { mutableStateOf("") }
    val startDate = rememberSaveable { mutableStateOf("") }
    val startTime = rememberSaveable { mutableStateOf("") }
    val endDate = rememberSaveable { mutableStateOf("") }
    val endTime = rememberSaveable { mutableStateOf("") }
    val Ubication = rememberSaveable { mutableStateOf("") }
    val selectedCategories = rememberSaveable { mutableStateOf(setOf<String>()) }
    val imageUrl = rememberSaveable { mutableStateOf("") }
    val dateError = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = Title,
                color = Color.White,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 32.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(20.dp, 20.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
        ) {
            item { EventName(Name) }
            item { EventDescription(description = Description) }
            item { EventDate(true, startDate, startTime, startDate, endDate, dateError) }
            item { EventDate(false, endDate, endTime, startDate, endDate, dateError) }
            item { CategoriasCarrusel(selectedCategories = selectedCategories, onCategorySelect = { updatedCategories -> selectedCategories.value =
                updatedCategories.toMutableList().toSet()
            }) }
            item { AddPhoto(imageUrl) }
            item { Row (modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)){/*TODO*/} }
            item { AddUbication(ubication = Ubication) }
            item { SaveElement(Name, Description, startDate, startTime ,endDate, endTime, selectedCategories, Ubication, authorName, autorId, imageUrl, dateError) }
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}
