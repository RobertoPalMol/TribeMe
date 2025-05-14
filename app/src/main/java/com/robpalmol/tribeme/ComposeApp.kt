package com.robpalmol.tribeme

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.robpalmol.tribeme.Components.TribeDetailScreen
import com.robpalmol.tribeme.Screens.CreateTribe
import com.robpalmol.tribeme.Screens.HomeScreen
import com.robpalmol.tribeme.Screens.LogIn
import com.robpalmol.tribeme.Screens.MyDataScreen
import com.robpalmol.tribeme.Screens.ProfileScreen
import com.robpalmol.tribeme.Screens.Register
import com.robpalmol.tribeme.Screens.SearchScreen
import com.robpalmol.tribeme.ViewModels.LoginViewModel
import com.robpalmol.tribeme.ViewModels.MyViewModel
import com.robpalmol.tribeme.ui.theme.BlackListOfBackground
import com.robpalmol.tribeme.ui.theme.BlackPost
import com.robpalmol.tribeme.ui.theme.BluePost
import com.robpalmol.tribeme.ui.theme.DifuminatedBackground
import com.robpalmol.tribeme.ui.theme.WhitePost

class Route(
    val name: String,
    val iconoDef: Int? = null,
    val iconoSel: Int? = null,
    val showMenu: Boolean = true,
    val showInMenu: Boolean = true
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposeApp() {
    val navController = rememberNavController()

    val configuration = LocalConfiguration.current
    val spaceBetweenNavBarItems = configuration.screenWidthDp.dp / 5

    val viewModel: MyViewModel = viewModel()

    val routes = listOf(
        Route("Inicio", R.drawable.home_outlined, R.drawable.home_solid),
        Route("Buscar", R.drawable.magnifying_glass_outlined, R.drawable.magnifying_glass_solid),
        Route("Crear", R.drawable.plus_square_outlined, R.drawable.plus_square_solid),
        Route("Tribus", R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground),
        Route("Usuario", R.drawable.profile_outlined, R.drawable.profile_outlined),
        Route("detail/{tribeId}", showInMenu = false, showMenu = false),
        Route("Login", showInMenu = false, showMenu = false),
        Route("Register", showInMenu = false, showMenu = false),
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (currentRoute == "Login" || currentRoute == "Register") {
                    Brush.verticalGradient(colors = DifuminatedBackground)
                } else {
                    Brush.verticalGradient(colors = BlackListOfBackground)
                }
            )
    ) {
        NavHost(
            navController = navController,
            startDestination = "Login",
            enterTransition = {
                slideIn(
                    animationSpec = tween(800),
                    initialOffset = { fullSize ->
                        IntOffset(0, fullSize.height)
                    }
                )
            },
            exitTransition = {
                slideOut(
                    animationSpec = tween(1600),
                    targetOffset = { fullSize ->
                        IntOffset(0, fullSize.height)
                    },
                )
            }) {
            composable("Inicio") { backStackEntry ->
                val reloadKey = backStackEntry.destination.route + System.currentTimeMillis()
                HomeScreen(
                    onItemClick = { selectedTribe ->
                        navController.navigate("detail/${selectedTribe.tribuId}")
                    },
                    viewModel = viewModel,
                    reloadKey = reloadKey
                )
            }
            composable("detail/{tribeId}") { backStackEntry ->
                val tribeId = backStackEntry.arguments?.getString("tribeId")?.toIntOrNull()

                if (tribeId != null) {
                    val tribe = viewModel.getTribeById(tribeId)

                    if (tribe != null) {
                        TribeDetailScreen(tribe, viewModel)
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Cargando tribu...", color = WhitePost)
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("ID de tribu inválido")
                    }
                }
            }
            composable(route = "Buscar") {
                SearchScreen()
            }
            composable(route = "Crear") {
                CreateTribe(
                    Title = "Crear Tribu",
                    viewModel = viewModel
                )
            }
            composable(route = "Tribus") {
                MyDataScreen()
            }
            composable(route = "Usuario") {
                ProfileScreen(navController, viewModel)
            }
            composable(route = "Login") {
                LogIn(navController)
            }
            composable(route = "Register") {
                Register(navController)
            }
        }
    }

    val currentAnimatedPos = animateFloatAsState(
        targetValue = (6 + (spaceBetweenNavBarItems.value * routes.indexOfFirst { it.name == navController.currentBackStackEntry?.destination?.route!! })).toFloat(),
        label = "Animación de menú"
    )

    if (routes[routes.indexOfFirst { it.name == navController.currentBackStackEntry?.destination?.route!! }].showMenu) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp, 20.dp))
                    .background(BluePost)
                    .padding(top = 15.dp, bottom = 35.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
            ) {
                routes.forEachIndexed { index, item ->
                    if (item.showInMenu) {
                        val interactionSource = remember { MutableInteractionSource() }
                        Column(
                            modifier = Modifier
                                .weight(weight = 1f)
                                .fillMaxWidth()
                        ) {
                            Box {
                                Row {
                                    Column(
                                        Modifier
                                            .weight(weight = 1.5f)
                                            .clickable(
                                                interactionSource = interactionSource,
                                                indication = null
                                            ) {
                                                navController.navigate(routes[index].name) {
                                                    // Si llegamos al primer elemento de la lista de rutas, la colocamos en
                                                    // primera posición del enrutado para limpiar la pila de rutas
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        //saveState = true
                                                    }
                                                    // Evitamos múltiples copias del mismo destino cuando volvamos a la misma ruta
                                                    launchSingleTop = true
                                                    // Restaurar estado cuando reseleccionemos una ruta previamente seleccionada
                                                    restoreState = true
                                                };
                                                //currentRoute = routes[index].name;
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally

                                    ) {

                                        Icon(
                                            modifier = Modifier.size(28.dp),
                                            painter = painterResource(item.iconoDef!!),
                                            contentDescription = null,
                                            tint = WhitePost
                                        )
                                    }
                                }

                            }
                            Spacer(
                                modifier = Modifier
                                    .height(20.dp)
                            )

                        }
                    }
                }
            }
            Box(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
                Row(
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 35.dp)
                        .fillMaxWidth()
                        .align(alignment = Alignment.BottomCenter)
                ) {
                    Box(
                        modifier = Modifier
                            .absoluteOffset(x = (currentAnimatedPos.value).toInt().dp)
                            .shadow(10.dp)
                            .width(70.dp)
                            .height(75.dp)
                            .fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(Color.White)

                    ) {
                        Row() {
                            Column(
                                Modifier
                                    .weight(weight = 1f)
                                    .padding(top = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(routes[routes.indexOfFirst { it.name == navController.currentBackStackEntry?.destination?.route }].iconoSel!!),
                                    contentDescription = null

                                )

                                Text(
                                    text = navController.currentBackStackEntry?.destination?.route!!,
                                    color = BlackPost,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}