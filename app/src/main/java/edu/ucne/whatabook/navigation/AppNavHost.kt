package edu.ucne.whatabook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.whatabook.presentation.carrito.CartViewModel
import edu.ucne.whatabook.presentation.compra.CompraViewModel
import edu.ucne.whatabook.presentation.home.HomeViewModel
import edu.ucne.whatabook.presentation.libros.LibroViewModel
import edu.ucne.whatabook.presentation.screens.SplashScreen
import edu.ucne.whatabook.presentation.screens.cartscreen.CartScreen
import edu.ucne.whatabook.presentation.screens.cartscreen.PagoScreen
import edu.ucne.whatabook.presentation.screens.compraScreen.HistorialComprasScreen
import edu.ucne.whatabook.presentation.screens.libroscreen.CrearLibroScreen
import edu.ucne.whatabook.presentation.screens.libroscreen.HomeScreen
import edu.ucne.whatabook.presentation.screens.libroscreen.LibroDetailScreen
import edu.ucne.whatabook.presentation.screens.userscreen.LoginScreen
import edu.ucne.whatabook.presentation.screens.userscreen.ProfileScreen
import edu.ucne.whatabook.presentation.screens.userscreen.RegisterScreen
import edu.ucne.whatabook.presentation.usuario.UsuarioViewModel
import kotlinx.coroutines.delay

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "splash"
) {

    val usuarioViewModel: UsuarioViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("splash") {
            val currentUserId by usuarioViewModel.userSessionId.collectAsState()
            val navigationAttempted = remember { mutableStateOf(false) }

            LaunchedEffect(currentUserId) {
                if (navigationAttempted.value) return@LaunchedEffect

                if (currentUserId > 0) {
                    navController.navigate("home") { popUpTo("splash") { inclusive = true } }
                    navigationAttempted.value = true
                } else if (currentUserId == 0) {
                    delay(100)
                    if (usuarioViewModel.userSessionId.value == 0) {
                        navController.navigate("login") { popUpTo("splash") { inclusive = true } }
                        navigationAttempted.value = true
                    }
                }
            }
            SplashScreen()
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = hiltViewModel(),
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeUiState by homeViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                homeViewModel.loadLibros()
            }

            HomeScreen(
                uiState = homeUiState,
                onSearch = homeViewModel::search,
                onDetalleLibro = { libro -> navController.navigate("detalle/${libro.libroId}") },
                onAgregarCarrito = { libro ->
                    cartViewModel.addLibroToCart(libro)
                    navController.navigate("carrito")
                },
                onCarritoClick = { navController.navigate("carrito") },
                onPerfilClick = { navController.navigate("perfil") },
                onFiltrarGenero = homeViewModel::filtrarPorGenero,
                onCrearLibroClick = { navController.navigate("crearLibro") }
            )
        }

        composable("crearLibro") {
            val libroViewModel: LibroViewModel = hiltViewModel()
            CrearLibroScreen(
                viewModel = libroViewModel,
                onVolver = { navController.popBackStack() }
            )
        }

        composable("carrito") {
            val cartUiState by cartViewModel.uiState.collectAsState()
            val userIdForCart by usuarioViewModel.userSessionId.collectAsState()

            CartScreen(
                uiState = cartUiState,
                onRemoveItem = cartViewModel::removeItem,
                onClearCart = {
                    cartViewModel.clearCart(userIdForCart)
                },
                onSelectPayment = cartViewModel::selectPaymentMethod,
                onNavigate = navController::navigate,
                onIncreaseQuantity = cartViewModel::increaseItemQuantity,
                onDecreaseQuantity = cartViewModel::decreaseItemQuantity
            )
        }

        composable("detalle/{libroId}") { backStackEntry ->
            val libroId = backStackEntry.arguments?.getString("libroId")?.toInt() ?: 0
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeUiState by homeViewModel.uiState.collectAsState()

            val libro = homeUiState.libros.find { it.libroId == libroId }

            libro?.let {
                LibroDetailScreen(
                    libro = it,
                    onAgregarCarrito = {
                        cartViewModel.addLibroToCart(it)
                        navController.navigate("carrito")
                    },
                    onVolver = { navController.popBackStack() }
                )
            }
        }

        composable("pago") {
            val cartUiState by cartViewModel.uiState.collectAsState()
            val userIdForPago by usuarioViewModel.userSessionId.collectAsState()

            PagoScreen(
                uiState = cartUiState,
                onValueChange = cartViewModel::updatePaymentFields,
                onConfirmarCompra = {
                    cartViewModel.confirmarCompra(userIdForPago)
                    navController.navigate("home")
                },
                onNavigate = navController::navigate
            )
        }

        composable("perfil") {
            ProfileScreen(
                onNavigateToHistory = { navController.navigate("historial") },
                onNavigate = { route ->
                    if (route == "login") {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    } else {
                        navController.navigate(route)
                    }
                }
            )
        }

        composable("historial") {
            val compraViewModel: CompraViewModel = hiltViewModel()
            HistorialComprasScreen(
                viewModel = compraViewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}