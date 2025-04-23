package com.robpalmol.tribeme.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.GrayLetter
import com.robpalmol.tribeme.ui.theme.WhitePost

@Preview
@Composable
fun SearchBar() {
    // Mantener el estado del valor del TextField
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }

    var icon = R.drawable.magnifying_glass_outlined
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(WhitePost, shape = RoundedCornerShape(20.dp))
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                TextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    textStyle = TextStyle(color = GrayLetter, fontSize = 16.sp),
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
                            text = "Busca un evento",
                            style = TextStyle(color = Color.Gray)
                        )
                    }
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(start = 8.dp),
                    tint = BlackPost
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoriasCarruselSearch() {
    var selectedCategory by remember { mutableStateOf(-1) } // Mantener solo un índice seleccionado (-1 significa ninguna)
    Column {
        Spacer(modifier = Modifier.height(15.dp))

        Row {
            Spacer(modifier = Modifier.width(30.dp))

            Text(
                text = "Filtrar por categorias:",
                style = TextStyle(fontSize = 20.sp),
                color = WhitePost
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow {
            items(category_icons.size) { index ->
                if (index == 0) {
                    Spacer(modifier = Modifier.width(15.dp))
                }

                CategoryButton(
                    icon = category_icons[index],
                    isSelected = index == selectedCategory,
                    onClick = {
                        selectedCategory = index
                    },
                    name = categoryNames[index]
                )

                if (index < category_icons.size) {
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }
}