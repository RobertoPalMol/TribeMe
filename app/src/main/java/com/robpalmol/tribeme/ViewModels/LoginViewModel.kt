package com.robpalmol.tribeme.ViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.robpalmol.tribeme.DataBase.Models.AuthResponse
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.DataBase.RetrofitInstance
import com.robpalmol.tribeme.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState: StateFlow<AuthResponse?> = _authState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loginUserAndNavigate(
        navController: NavHostController,
        email: String,
        password: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                // Hacer login
                val user = User(0, "", email, password, "")
                val response = RetrofitInstance.getApiService(context).loginUser(user)

                // Si el token es válido, navegamos a la pantalla principal
                if (response.token.isNotEmpty()) {
                    SessionManager(context).saveToken(response.token)
                    _authState.value = response
                    navController.navigate("Inicio")
                } else {
                    _error.value = "Credenciales inválidas"
                }
            } catch (e: Exception) {
                if (e.message == "HTTP 401 Unauthorized") {
                    _error.value = "Credenciales inválidas"
                }
                _error.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }

    fun registerUserAndNavigate(
        navController: NavHostController,
        name: String,
        email: String,
        password: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val user = User(0, name, email, password, "")
                val response = RetrofitInstance.getApiService(context).registerUser(user)
                Log.d("SignInDebug", "Token: ${response.token}, User: ${response.user}")

                if (!response.token.isNullOrEmpty()) {
                    Log.d("SignIn", "Cuenta creada con éxito")
                    Toast.makeText(context, "Cuenta creada con éxito", Toast.LENGTH_LONG).show()
                    _authState.value = response
                    SessionManager(context).saveToken(response.token)
                    navController.navigate("Inicio")
                } else {
                    Log.e("SignIn", "Token inválido: ${response.token}")
                    _error.value = "Error al registrar: respuesta inválida"
                }

            } catch (e: Exception) {
                Log.e("SignInError", "Exception: ${e.localizedMessage}", e)
                _error.value = "Error al registrar: ${e.message}"
            }
        }
    }
}

