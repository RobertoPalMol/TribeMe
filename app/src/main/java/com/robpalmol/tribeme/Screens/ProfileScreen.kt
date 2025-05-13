package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.robpalmol.tribeme.Components.ActiveElements
import com.robpalmol.tribeme.Components.CloseSesion
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Composable
fun ProfileScreen(
    navController: NavController,
    name: MutableState<String>,
    currentEventID: MutableState<String>,
    autorId: MutableState<String>
) {
    var showDialog by remember { mutableStateOf(false) }
    val events = "viewModel.events"
    val autorID = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPost)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = name.value,
                color = Color.White,
                style = TextStyle(fontSize = 32.sp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.verticalGradient(DifuminatedBackground))
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                ActiveElements()
                Spacer(modifier = Modifier.height(5.dp))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }

                // your events list
                /*
                items(events) { event ->
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                currentEventID.value = event.id
                                Log.d("PostElement", currentEventID.value)
                                navController.navigate("HomeExtended")
                            },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (autorId.value == event.authorId) {
                            Log.d("PostElement1", event.authorId)
                            Log.d("PostElement1", autorId.value)
                            YourPostElement(
                                onDeleteClick = { showDialog = true },
                                navController = navController,
                                event = event,
                                autorID = autorID
                            )
                        }else{
                            Log.d("PostElement2", event.authorId)
                            Log.d("PostElement2", autorId.value)
                        }
                    }
                }

                 */

                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }

                //close sesion button
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    CloseSesion(navController = navController, name = name)
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
        }
    }
}