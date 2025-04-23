package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robpalmol.tribeme.Components.CategoriasCarruselSearch
import com.robpalmol.tribeme.Components.SearchBar
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Preview
@Composable
fun SearchScreen(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BlackPost)) {
        Spacer(modifier = Modifier
            .height(40.dp))
        Row {
            Spacer(modifier = Modifier
                .width(10.dp))
            SearchBar()
            Spacer(modifier = Modifier
                .width(10.dp))
        }

        CategoriasCarruselSearch()

        Spacer(modifier = Modifier
            .height(10.dp))

        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp, 20.dp))
            .background(Brush.verticalGradient(DifuminatedBackground))) {
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                Row {
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                    )

                }
            }
        }
    }
}