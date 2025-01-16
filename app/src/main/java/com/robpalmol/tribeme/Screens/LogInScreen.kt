package com.robpalmol.tribeme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.robpalmol.tribeme.Components.CreateSesion
import com.robpalmol.tribeme.Components.NoAccount
import com.robpalmol.tribeme.Components.Password
import com.robpalmol.tribeme.Components.PasswordForgoten
import com.robpalmol.tribeme.Components.StartSesion
import com.robpalmol.tribeme.Components.UserMailDirection
import com.robpalmol.tribeme.Components.UserMailName
import com.robpalmol.tribeme.Components.UserName
import com.robpalmol.tribeme.Components.YesAccount
import com.robpalmol.tribeme.Components.divisor
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Composable
fun LogIn(navController: NavHostController) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(DifuminatedBackground)
            )
    ) {
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Spacer(modifier = Modifier.width(100.dp))
                    Image(
                        painterResource(id = R.drawable.eye_show),
                        contentDescription = "",
                        modifier = Modifier.size(200.dp, 200.dp)
                    )
                }
                Column {
                    Spacer(modifier = Modifier.height(290.dp))
                    UserMailName(email)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Password(password)
            Column {
                PasswordForgoten()
                Spacer(modifier = Modifier.height(5.dp))
                StartSesion(text = "Iniciar session", navController)
                Spacer(modifier = Modifier.height(20.dp))
                divisor()
                Spacer(modifier = Modifier.height(20.dp))
                /*
                GoogleStartSesion(text = "Iniciar sesión con Google")
                */
                Spacer(modifier = Modifier.height(20.dp))
                NoAccount(navController)
            }
        }
    }
}


@Composable
fun Register(navController2: NavHostController) {

    val name = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(DifuminatedBackground)
            )
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Spacer(modifier = Modifier.width(110.dp))
                    Image(
                        painterResource(id = R.drawable.eye_show),
                        contentDescription = "",
                        modifier = Modifier.size(180.dp, 104.dp)
                    )
                }
                Column {
                    Spacer(modifier = Modifier.height(200.dp))
                    UserMailDirection(email)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            UserName(name)
            Spacer(modifier = Modifier.height(20.dp))
            Password(password)
            Spacer(modifier = Modifier.height(20.dp))
            CreateSesion(navHostController = navController2)
            Spacer(modifier = Modifier.height(20.dp))
            divisor()
            Spacer(modifier = Modifier.height(20.dp))
            /*
            GoogleStartSesion("Registrarse con Google")
             */
            Spacer(modifier = Modifier.height(20.dp))
            YesAccount(navController2)
        }
    }
}