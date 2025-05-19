package com.robpalmol.tribeme.ViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robpalmol.tribeme.DataBase.Models.CreateTribeRequest
import com.robpalmol.tribeme.DataBase.Models.EventoDTO
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.TribuDTO
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.DataBase.RetrofitInstance
import com.robpalmol.tribeme.util.JwtUtils
import com.robpalmol.tribeme.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _userData = MutableStateFlow<List<User>>(emptyList())
    val userData: StateFlow<List<User>> = _userData

    private val _tribeData = MutableStateFlow<List<Tribe>>(emptyList())
    val tribeData: StateFlow<List<Tribe>> = _tribeData

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _eventos = MutableStateFlow<List<EventoDTO>>(emptyList())
    val eventos: StateFlow<List<EventoDTO>> = _eventos

    fun getAllUsers(context: Context) {
        viewModelScope.launch {
            try {
                val users = RetrofitInstance.getApiService(context).getAllUsers()
                _userData.value = users
            } catch (e: Exception) {
                _error.value = "No se pudo obtener los usuarios: ${e.localizedMessage}"
                Log.d("MyViewModel", "Error al obtener los usuarios: $e")
            }
        }
    }

    fun getAllTribes(context: Context) {
        viewModelScope.launch {
            try {
                val tribes = RetrofitInstance.getApiService(context).getAllTribes()
                Log.d("MyViewModel", tribes.toString())
                _tribeData.value = tribes
            } catch (e: Exception) {
                _error.value = "No se pudo obtener las tribus: ${e.localizedMessage}"
                Log.d("MyViewModel", "Error al obtener las tribus: $e")
            }
        }
    }

    fun getTribeById(id: Int): Tribe? {
        Log.d("ViewModel", "Buscando tribu con ID: $id en lista: ${tribeData.value.map { it.tribuId }}")
        return tribeData.value.find { it.tribuId.toInt() == id }
    }

    fun createTribe(
        context: Context,
        createRequest: CreateTribeRequest,
        onSuccess: (Tribe) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                var currentUser: User? = null

                val tribuDTO = TribuDTO(
                    nombre = createRequest.nombre,
                    descripcion = createRequest.descripcion,
                    imagenUrl = createRequest.imagenUrl,
                    numeroMaximoMiembros = createRequest.numeroMaximoMiembros,
                    esPrivada = createRequest.esPrivada,
                    categorias = createRequest.categorias,
                    autorId = currentUser?.usuarioId ?: 0
                )

                val createdTribe = RetrofitInstance.getApiService(context).crearTribu(tribuDTO)

                _tribeData.value = _tribeData.value + createdTribe

                onSuccess(createdTribe)
            } catch (e: Exception) {
                val errorMsg = "Error al crear la tribu: ${e.localizedMessage}"
                _error.value = errorMsg
                onError(errorMsg)
                Log.e("MyViewModel", errorMsg, e)
            }
        }
    }
    fun loadCurrentUser(context: Context) {
        val token = SessionManager(context).getToken()
        if (!token.isNullOrEmpty()) {
            try {
                val user = JwtUtils.decodeToken(token)
                _currentUser.value = user
            } catch (e: Exception) {
                _error.value = "Error al cargar el usuario: ${e.localizedMessage}"
                Log.e("MyViewModel", "Error al decodificar el token: ${e.localizedMessage}", e)
            }
        } else {
            _error.value = "No se encontró el token"
            Log.e("MyViewModel", "Token no encontrado")
        }
    }
    fun getUserTribes() {
        val userId = _currentUser.value?.usuarioId
        if (userId != null) {
            _tribeData.value = _tribeData.value.filter { it.autorId == userId.toString() }
        }
    }

    fun getAllEventos(context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService(context).getAllEventos()
                _eventos.value = response
            } catch (e: Exception) {
                _error.value = "Error al obtener eventos: ${e.localizedMessage}"
                Log.e("MyViewModelEvent", "Error en eventos", e)
            }
        }
    }

    fun unirseATribu(
        context: Context,
        tribuId: Long,
        usuarioId: Long,
        miembros: List<User>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val yaEsMiembro = miembros.any { it.usuarioId == usuarioId }

            if (yaEsMiembro) {
                Toast.makeText(context, "Ya perteneces a esta tribu", Toast.LENGTH_SHORT).show()
                return@launch
            }

            try {
                val response = RetrofitInstance.getApiService(context).unirseATribu(tribuId, usuarioId)

                if (response.isSuccessful) {
                    Toast.makeText(context, "¡Te has unido a la tribu!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                } else {
                    onError("Error al unirse: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.localizedMessage}")
            }
        }
    }
}

