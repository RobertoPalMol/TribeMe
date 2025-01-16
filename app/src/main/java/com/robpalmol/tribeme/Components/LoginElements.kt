package com.robpalmol.tribeme.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.WhitePost
import kotlinx.coroutines.launch


@Composable
fun Password(
    password: MutableState<String> = rememberSaveable { mutableStateOf("") }
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(WhitePost, shape = RoundedCornerShape(20.dp))
                    .padding(8.dp)
                    .size(300.dp, 60.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(WhitePost)
                ) {
                    TextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Contraseña", fontSize = 15.sp) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        textStyle = TextStyle(BlackPost),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = WhitePost,
                            unfocusedContainerColor = WhitePost,
                            disabledContainerColor = WhitePost,
                            cursorColor = BlackPost,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLabelColor = BlackPost,
                            disabledLabelColor = BlackPost,
                        )
                    )
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            val eye =
                                if (!passwordVisible) R.drawable.eye_off else R.drawable.eye_show
                            Image(
                                painterResource(id = eye),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(20.dp)
                                    .align(Alignment.Center)
                                    .clickable { passwordVisible = !passwordVisible }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PasswordForgoten() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Olvidaste tu contraseña?",
            textAlign = TextAlign.Center,
            color = BluePost,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    /*TODO*/
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMailName(mailname: MutableState<String>) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(WhitePost, shape = RoundedCornerShape(20.dp))
                .padding(8.dp)
                .size(300.dp, 60.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(WhitePost)
            ) {
                TextField(
                    value = mailname.value,
                    onValueChange = { mailname.value = it },
                    label = {
                        Text(
                            "Correo o nombre de usuario",
                            fontSize = 15.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent, //hide the indicator
                        unfocusedIndicatorColor = Color.Transparent, //hide indicator when the text is used
                        containerColor = WhitePost,
                        focusedLabelColor = BlackPost
                    ),
                    modifier = Modifier
                        .height(60.dp)
                        .horizontalScroll(rememberScrollState())//improve el scroll
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMailDirection(mail: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(WhitePost, shape = RoundedCornerShape(20.dp))
                .padding(8.dp)
                .size(300.dp, 60.dp)
        ) {
            Row {
                TextField(
                    value = mail.value,
                    onValueChange = { mail.value = it },
                    label = {
                        Text(
                            "Dirección de correo electrónico",
                            fontSize = 15.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent, //hide the indicator
                        unfocusedIndicatorColor = Color.Transparent, //hide indicator when the text is used
                        containerColor = WhitePost,
                        focusedLabelColor = BlackPost
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserName(name: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(WhitePost, shape = RoundedCornerShape(20.dp))
                .padding(8.dp)
                .size(300.dp, 60.dp)
        ) {
            Row {
                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = {
                        Text(
                            "Nombre de usuario",
                            fontSize = 15.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent, //hide the indicator
                        unfocusedIndicatorColor = Color.Transparent, //hide indicator when the text is used
                        containerColor = WhitePost,
                        focusedLabelColor = BlackPost
                    )
                )
            }
        }
    }
}

@Composable
fun CreateSesion(navHostController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    // Registro exitoso, navegar a la pantalla de inicio
                    navHostController.navigate("Home")
                }
            },
            colors = ButtonDefaults.buttonColors(BluePost),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
        ) {
            Text(
                text = "Registrarme",
                textAlign = TextAlign.Center,
                color = WhitePost,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun NoAccount(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "¿Todavia no tienes cuenta?",
                color = BlackPost,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }

        Text(
            text = "Registrarse aquí.",
            textAlign = TextAlign.Center,
            color = BluePost,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable { navController.navigate("Register") }
        )
    }
}

@Composable
fun YesAccount(navController2: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "¡Ya tengo cuenta!",
                color = BlackPost,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }
        Text(
            text = "Iniciar sesión.",
            textAlign = TextAlign.Center,
            color = BluePost,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable { navController2.navigate("LogIn") }
        )
    }
}

@Composable
fun StartSesion(
    text: String,
    navHostController: NavHostController,
    name: String? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                if (name.isNullOrEmpty()) {

                } else {
                    navHostController.navigate("Register")
                }
            },
            colors = ButtonDefaults.buttonColors(BluePost),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = WhitePost,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun divisor() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Divider(
            color = BlackPost,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "o",
            modifier = Modifier.padding(horizontal = 8.dp),
            color = BlackPost
        )
        Divider(
            color = BlackPost,
            modifier = Modifier.weight(1f)
        )
    }
}

/*
@Composable
fun GoogleStartSesion(
    text: String,
) {
    Button(
        onClick = {/*TODO*/
        },
        modifier = Modifier
            .width(400.dp)
            .height(60.dp)
            .padding(horizontal = 50.dp),
        colors = ButtonDefaults.buttonColors(WhitePost),
        border = BorderStroke(1.dp, BluePost)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_img),
                contentDescription = "Google Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.Black
            )
        }
    }
}

 */