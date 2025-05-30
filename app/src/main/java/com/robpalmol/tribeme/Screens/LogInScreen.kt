package com.robpalmol.tribeme.Screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.robpalmol.tribeme.Components.CreateSesion
import com.robpalmol.tribeme.Components.NoAccount
import com.robpalmol.tribeme.Components.Password
import com.robpalmol.tribeme.Components.StartSesion
import com.robpalmol.tribeme.Components.UserMailDirection
import com.robpalmol.tribeme.Components.UserMailName
import com.robpalmol.tribeme.Components.UserName
import com.robpalmol.tribeme.Components.YesAccount
import com.robpalmol.tribeme.Components.divisor
import com.robpalmol.tribeme.R
import com.robpalmol.tribeme.ViewModels.LoginViewModel
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground

@Composable
fun LogIn(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    // Obtener el contexto actual
    val context = LocalContext.current

    // Observar el estado de autenticación
    val authState by loginViewModel.authState.collectAsState()
    val errorState by loginViewModel.error.collectAsState()

    // Mostrar un mensaje si hubo un error
    LaunchedEffect(errorState) {
        if (!errorState.isNullOrEmpty()) {
            // Mostrar un Toast o un mensaje con el error
            Toast.makeText(context, errorState, Toast.LENGTH_LONG).show()
            Log.d("LoginError", errorState!!)
        }
    }

    // Mostrar un mensaje si el inicio de sesión fue exitoso
    LaunchedEffect(authState) {
        if (authState != null) {
            // Puedes mostrar un mensaje de éxito o navegar
            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
            Log.d("LoginSuccess", authState.toString())
        }
    }

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
                        painterResource(id = R.drawable.tribeme_image),
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
                //PasswordForgoten()
                Spacer(modifier = Modifier.height(25.dp))
                StartSesion(
                    text = "Iniciar sesión",
                    navHostController = navController,
                    name = null,
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
fun Register(navController2: NavHostController) {

    val loginViewModel: LoginViewModel = viewModel()

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
                        painterResource(id = R.drawable.tribeme_image),
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
            CreateSesion(
                navHostController = navController2,
                loginViewModel = loginViewModel,
                name = name.value,
                email = email.value,
                password = password.value
            )
            Spacer(modifier = Modifier.height(20.dp))
            divisor()
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(modifier = Modifier.height(20.dp))
            YesAccount(navController2)
        }
    }
}