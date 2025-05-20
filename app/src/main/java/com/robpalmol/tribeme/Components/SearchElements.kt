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


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .background(WhitePost, shape = RoundedCornerShape(20.dp))
            .padding(8.dp),
        placeholder = { Text(text = "Busca un evento", color = Color.Gray) },
        textStyle = TextStyle(color = GrayLetter, fontSize = 16.sp),
        colors = TextFieldDefaults.colors(
            cursorColor = BlackPost,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = WhitePost,
            unfocusedContainerColor = WhitePost,
        ),
        trailingIcon = {
            Icon(
                painterResource(id = R.drawable.magnifying_glass_outlined),
                contentDescription = null,
                tint = BlackPost,
                modifier = Modifier.size(45.dp)
            )
        }
    )
}


@Composable
fun CategoriasCarruselSearch(
    selectedCategoryIndex: Int,
    onCategorySelected: (Int) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "Filtrar por categorías:",
                style = TextStyle(fontSize = 20.sp),
                color = WhitePost
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        LazyRow {
            items(category_icons.size) { index ->
                if (index == 0) Spacer(modifier = Modifier.width(15.dp))
                CategoryButton(
                    icon = category_icons[index],
                    isSelected = index == selectedCategoryIndex,
                    onClick = { onCategorySelected(index) },
                    name = categoryNames[index]
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
}
