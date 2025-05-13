package com.robpalmol.tribeme.Components

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ViewModels.LoginViewModel
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.WhitePost


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
                        label = { Text("Contraseña", fontSize = 15.sp, fontWeight = FontWeight.Bold)},
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
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(BlackPost),
                    colors = TextFieldDefaults.colors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = BlackPost,
                        focusedContainerColor = WhitePost,
                        unfocusedContainerColor = WhitePost,
                        disabledContainerColor = WhitePost
                    )
                    ,
                    modifier = Modifier
                        .height(60.dp)
                        .horizontalScroll(rememberScrollState())//improve el scroll
                )
            }
        }
    }
}

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
                    colors = TextFieldDefaults.colors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = BlackPost,
                        focusedContainerColor = WhitePost,
                        unfocusedContainerColor = WhitePost,
                        disabledContainerColor = WhitePost
                    )

                )
            }
        }
    }
}

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
                    colors = TextFieldDefaults.colors(
                        cursorColor = BlackPost,
                        disabledLabelColor = BlackPost,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = BlackPost,
                        focusedContainerColor = WhitePost,
                        unfocusedContainerColor = WhitePost,
                        disabledContainerColor = WhitePost
                    )

                )
            }
        }
    }
}

@Composable
fun CreateSesion(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = viewModel(),
    name: String,
    email: String,
    password: String
) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                loginViewModel.registerUserAndNavigate(
                    navController = navHostController,
                    name = name,
                    email = email,
                    password = password,
                    context = context
                )
                Log.d("SignIn", "Cuenta creada con éxito")
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
    name: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onClick,
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