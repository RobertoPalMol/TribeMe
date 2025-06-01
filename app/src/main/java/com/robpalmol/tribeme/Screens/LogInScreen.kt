package com.robpalmol.tribeme.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.robpalmol.tribeme.Components.*
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ViewModels.LoginViewModel
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Composable
fun LogIn(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    val authState by loginViewModel.authState.collectAsState()
    val errorState by loginViewModel.error.collectAsState()

    LaunchedEffect(errorState) {
        if (!errorState.isNullOrEmpty()) {
            Toast.makeText(context, errorState, Toast.LENGTH_LONG).show()
            Log.d("LoginError", errorState!!)
        }
    }

    LaunchedEffect(authState) {
        if (authState != null) {
            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
            Log.d("LoginSuccess", authState.toString())
            // Aquí podrías navegar a otra pantalla si quieres
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(DifuminatedBackground))
    ) {
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Spacer(modifier = Modifier.width(100.dp))
                    Image(
                        painterResource(id = R.drawable.tribeme_image),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
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
                Spacer(modifier = Modifier.height(25.dp))
                StartSesion(
                    text = "Iniciar sesión",
                    navHostController = navController,
                    onClick = {
                        loginViewModel.loginUserAndNavigate(navController, email.value, password.value, context)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                divisor()
                Spacer(modifier = Modifier.height(20.dp))
                NoAccount(navController)
            }
        }
    }
}

@Composable
fun Register(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val name = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(DifuminatedBackground))
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Spacer(modifier = Modifier.width(110.dp))
                    Image(
                        painterResource(id = R.drawable.tribeme_image),
                        contentDescription = null,
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
            CreateSesion(
                navHostController = navController,
                loginViewModel = loginViewModel,
                name = name.value,
                email = email.value,
                password = password.value
            )
            Spacer(modifier = Modifier.height(20.dp))
            divisor()
            Spacer(modifier = Modifier.height(20.dp))
            YesAccount(navController)
        }
    }
}
