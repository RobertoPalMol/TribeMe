package com.robpalmol.tribeme.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import coil.ImageLoader
import com.robpalmol.tribeme.DataBase.Models.CreateEventoDTO
import com.robpalmol.tribeme.DataBase.Models.EventoDTO
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.TribuDTO
import com.robpalmol.tribeme.DataBase.Models.TribuUpdateDTO
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.DataBase.RetrofitInstance
import com.robpalmol.tribeme.util.JwtUtils
import com.robpalmol.tribeme.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class MyViewModel : ViewModel() {

    private val _tribeData = MutableStateFlow<List<Tribe>>(emptyList())
    val tribeData: StateFlow<List<Tribe>> = _tribeData

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _eventos = MutableStateFlow<List<EventoDTO>>(emptyList())
    val eventos: MutableStateFlow<List<EventoDTO>> = _eventos

    private val _eventosPorTribu = mutableStateMapOf<Long, List<EventoDTO>>()
    val eventosPorTribu: SnapshotStateMap<Long, List<EventoDTO>> get() = _eventosPorTribu

    private val _eventoSeleccionado = mutableStateOf<EventoDTO?>(null)
    val eventoSeleccionado: State<EventoDTO?> = _eventoSeleccionado


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

    fun getAllEvents(context: Context) {
        viewModelScope.launch {
            try {
                val events = RetrofitInstance.getApiService(context).getAllEvents()
                Log.d("MyViewModel", events.toString())
                _eventos.value = events
            } catch (e: Exception) {
                _error.value = "No se pudo obtener los eventos: ${e.localizedMessage}"
                Log.d("MyViewModel", "Error al obtener los eventos: $e")
            }
        }
    }

    fun getMyTribes(context: Context) {
        viewModelScope.launch {
            try {
                val myTribes = RetrofitInstance.getApiService(context).getMyTribes()
                Log.d("MyViewModelt", myTribes.toString())
                _tribeData.value = myTribes
            } catch (e: Exception) {
                _error.value = "No se pudo obtener mis tribus: ${e.localizedMessage}"
                Log.d("MyViewModelt", "Error al obtener mis tribus: $e")
            }
        }
    }


    fun getTribeById(id: Long): Tribe? {
        Log.d("ViewModel", "Buscando tribu con ID: $id en lista: ${tribeData.value.map { it.tribuId }}")
        return tribeData.value.find { it.tribuId == id }
    }

    fun getEventById(context: Context, id: Long) {
        viewModelScope.launch {
            try {
                val evento = RetrofitInstance.getApiService(context).getEventById(id)
                _eventoSeleccionado.value = evento
            } catch (e: Exception) {
                _eventoSeleccionado.value = null
            }
        }
    }



    fun createTribe(
        context: Context,
        createRequest: TribuDTO,
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
                    autorId = currentUser?.usuarioId ?: 0,
                    ubicacion = createRequest.ubicacion,
                    crearEventos = createRequest.crearEventos
                )
                Log.d("MyViewModel1", "privada: ${createRequest.esPrivada}")
                Log.d("MyViewModel1", "eventos: ${createRequest.crearEventos}")
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

    fun getEventosPorTribu(context: Context, tribuId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService(context).getEventosPorTribu(tribuId)
                _eventosPorTribu[tribuId] = response
            } catch (e: Exception) {
                _error.value = "Error al obtener eventos: ${e.localizedMessage}"
                Log.e("MyViewModelEvent", "Error en eventos", e)
            }
        }
    }


    fun crearEvento(context: Context, eventoDTO: CreateEventoDTO) {
        viewModelScope.launch {
            try {
                RetrofitInstance.getApiService(context).crearEvento(eventoDTO)
            } catch (e: Exception) {
                Log.e("CrearEvento", "Error: ${e.localizedMessage}", e)
            }
        }
    }

    fun unirseATribu(
        context: Context,
        tribuId: Long,
        usuarioId: Long,
        miembros: List<User>,
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
                } else {
                    onError("Error al unirse: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.localizedMessage}")
            }
        }
    }

    fun salirDeTribu(
        context: Context,
        tribuId: Long,
        usuarioId: Long,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService(context).salirDeTribu(tribuId, usuarioId)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Has salido de la tribu", Toast.LENGTH_SHORT).show()
                } else {
                    onError("Error al salir de la tribu: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.localizedMessage}")
            }
        }
    }

    fun buscarTribus(context: Context, query: String, categorias: List<String>? = null) {
        viewModelScope.launch {
            try {
                val trimmedQuery = query.trim()
                if (trimmedQuery.isEmpty() && (categorias == null || categorias.isEmpty())) {
                    _tribeData.value = emptyList()
                    _error.value = null
                    return@launch
                }
                val response = RetrofitInstance.getApiService(context)
                    .buscarTribus(nombre = trimmedQuery, ubicacion = trimmedQuery, categorias = categorias)
                _tribeData.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateTribe(tribeId: Long, request: TribuUpdateDTO, context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService(context = context).updateTribe(tribeId, request)
                if (response.isSuccessful) {
                    Log.d("UpdateTribe", "Tribu actualizada correctamente")
                } else {
                    Log.e("UpdateTribe", "Error en la respuesta: ${response.code()}")
                    Log.e("UpdateTribe", "Error en la respuesta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("UpdateTribe", "Error al actualizar la tribu", e)
            }
        }
    }
    fun eliminarTribu(tribuId: Long, onSuccess: () -> Unit, onError: (String) -> Unit, context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService(context = context).deleteTribu(tribuId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error al eliminar tribu: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Excepción al eliminar tribu: ${e.localizedMessage}")
            }
        }
    }

    fun subirImagen(
        uri: Uri,
        context: Context,
        onResult: (String?) -> Unit
    ) {
        viewModelScope.launch {
            val file = uriToFile(uri, context)
            if (file != null) {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

                try {
                    val response = RetrofitInstance.getApiService(context).uploadImage(body)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        onResult(responseBody?.imageUrl) // Pasa la URL remota o null si fallo
                    } else {
                        onResult(null)
                        Log.e("SaveElement", "Error al subir la imagen. Código: ${response.code()}, mensaje: ${response.message()}")

                    }
                } catch (e: Exception) {
                    onResult(null)
                    Log.e("SaveElement", "Error al subir la imagen ${e.message}", e)
                }
            } else {
                onResult(null)
            }
        }
    }



    private fun uriToFile(uri: Uri, context: Context): File? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

    fun obtenerUrlImagen(imagenDb: String): String {
        val baseUrl = RetrofitInstance.BASE_URL + "/api/tribus/imagenes/"
        val fileName = imagenDb.substringAfterLast("/")
        return baseUrl + fileName
    }

    fun createAuthenticatedImageLoader(context: Context, token: String): ImageLoader {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer $token")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .crossfade(true)
            .build()
    }

    fun unirseAEvento(
        context: Context,
        eventoId: Long,
        usuarioId: Long,
        participantes: List<User>,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val yaInscrito = participantes.any { it.usuarioId == usuarioId }

            if (yaInscrito) {
                Toast.makeText(context, "Ya estás inscrito en este evento", Toast.LENGTH_SHORT).show()
                return@launch
            }

            try {
                val response = RetrofitInstance.getApiService(context).unirseAEvento(eventoId, usuarioId)

                if (response.isSuccessful) {
                    Toast.makeText(context, "¡Te has unido al evento!", Toast.LENGTH_SHORT).show()
                } else {
                    onError("Error al unirse al evento: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.localizedMessage}")
            }
        }
    }

    fun salirDeEvento(
        context: Context,
        eventoId: Long,
        usuarioId: Long,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService(context).salirDeEvento(eventoId, usuarioId)

                if (response.isSuccessful) {
                    Toast.makeText(context, "Has salido del evento", Toast.LENGTH_SHORT).show()
                } else {
                    onError("Error al salir del evento: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.localizedMessage}")
            }
        }
    }
}

