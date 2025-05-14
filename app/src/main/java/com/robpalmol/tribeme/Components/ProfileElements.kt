package com.robpalmol.tribeme.Components

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.WhitePost



@Composable
fun YourPostElement(onDeleteClick: () -> Unit, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var event by remember { mutableStateOf("Event") }
    Column(
        modifier = Modifier
            .width(350.dp)
            .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
            .background(WhitePost)
            .padding(10.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = BlackPost
            )
            Text(
                text = "@${event}",
                fontSize = 14.sp,
                color = BluePost
            )
        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Row {
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
            Column {
            }
        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        LazyRow {
            item {
                Text(
                    text = "Categorías:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("Crear") },
                colors = ButtonDefaults.buttonColors(BluePost),
                modifier = Modifier
                    .height(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.pen_edit_publication),
                    contentDescription = "Editar Icon",
                    modifier = Modifier
                        .size(18.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Editar publicación",
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { showDialog = true
                    onDeleteClick() },
                colors = ButtonDefaults.buttonColors(BluePost),
                modifier = Modifier
                    .height(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.bin_edit_publication),
                    contentDescription = "Editar Icon",
                    modifier = Modifier
                        .size(18.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Eliminar publicación",
                    fontSize = 10.sp
                )
            }
        }
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {
            DeletePoppup(NoClick = { showDialog = false })
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
fun DeletePoppup(NoClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(130.dp)
            .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
            .background(BlackPost)
            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
            .background(WhitePost)
            .padding(5.dp)){
            Text(text = "Seguro que desas eliminar el evento?")
            Spacer(modifier = Modifier.height(5.dp))
            Row (modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)){
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(BluePost)
                ) {
                    Text(text = "Si")
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    onClick = { NoClick() },
                    colors = ButtonDefaults.buttonColors(BluePost)
                ) {
                    Text(text = "No")
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

