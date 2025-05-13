package com.robpalmol.tribeme.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.robpalmol.tribeme.Components.AddPhoto
import com.robpalmol.tribeme.Components.BooleanTribe
import com.robpalmol.tribeme.Components.CategoriasCarrusel
import com.robpalmol.tribeme.Components.MaxMembers
import com.robpalmol.tribeme.Components.SaveElement
import com.robpalmol.tribeme.Components.TribeDescription
import com.robpalmol.tribeme.Components.TribeName
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTribe(
    Title: String,
    viewModel: MyViewModel
) {
    val Name = rememberSaveable { mutableStateOf("") }
    val Description = rememberSaveable { mutableStateOf("") }
    val members = rememberSaveable { mutableStateOf(0) }
    val selectedCategories = rememberSaveable { mutableStateOf(listOf<String>()) }
    val imageUrl = rememberSaveable { mutableStateOf("") }
    val private1 = remember { mutableStateOf(true) }
    val dateError = remember { mutableStateOf(false) }


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
                text = Title,
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
            item { TribeName(Name) }
            item { TribeDescription(description = Description) }
            item { MaxMembers(members = members) }
            item {
                CategoriasCarrusel(
                    selectedCategories = selectedCategories,
                    onCategorySelect = { updatedCategories ->
                        selectedCategories.value = updatedCategories.toMutableList()
                    }
                )
            }
            //item { AddPhoto(imageUrl) }
            item { BooleanTribe("Tribu privada", private1) }
            item { BooleanTribe("Los miembros podrán crear eventos", remember { mutableStateOf(true) }) }
            item {
                SaveElement(
                    name = Name,
                    description = Description,
                    imageUrl = imageUrl,
                    selectedCategories = selectedCategories,
                    private = private1,
                    members = members,
                    dateError = dateError,
                    context = context,
                    viewModel = viewModel
                )
            }
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}
