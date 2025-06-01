package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.robpalmol.tribeme.Components.CategoriasCarruselSearch
import com.robpalmol.tribeme.Components.SearchBar
import com.robpalmol.tribeme.Components.TribeElement
import com.robpalmol.tribeme.Components.categoryNames
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Composable
fun SearchScreen(viewModel: MyViewModel, onItemClick: (Tribe) -> Unit,) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableStateOf(-1) }
    var context = LocalContext.current

    val selectedCategoryName = if (selectedCategoryIndex >= 0) categoryNames[selectedCategoryIndex] else null

    LaunchedEffect(searchQuery, selectedCategoryIndex) {
        val categorias = selectedCategoryName?.let { listOf(it) }
        viewModel.buscarTribus(context = context, query = searchQuery, categorias = categorias)
    }

    val tribes by viewModel.tribeData.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.padding(horizontal = 10.dp)) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }

        CategoriasCarruselSearch(
            selectedCategoryIndex = selectedCategoryIndex,
            onCategorySelected = { selectedCategoryIndex = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp, 20.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(tribes) { tribe ->
                    TribeElement(tribe = tribe, onClick = { onItemClick(tribe) })
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item{
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
        }
    }
}
