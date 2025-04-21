package com.robpalmol.tribeme.ViewModels

import android.content.Context
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

    fun login(user: User, context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService(context).loginUser(user)
                if (response.token.isNotEmpty()) {
                    // Guardamos el token en la sesión
                    SessionManager(context).saveToken(response.token)
                    _authState.value = response
                } else {
                    _error.value = "Token vacío"
                }
            } catch (e: Exception) {
                _error.value = "Error en el login: ${e.message}"
            }
        }
    }

    fun loginUserAndNavigate(navController: NavHostController, email: String, password: String, context: Context) {
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
                _error.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }
}

