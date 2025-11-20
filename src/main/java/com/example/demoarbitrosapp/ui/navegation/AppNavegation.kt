package com.example.demoarbitrosapp.ui.navegation // O 'navigation', según el nombre de tu carpeta

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demoarbitrosapp.ui.screen.ArbitroListScreen
import com.example.demoarbitrosapp.ui.screen.LoginScreen
import com.example.demoarbitrosapp.ui.screen.RegisterScreen

// Definimos las rutas de una forma limpia y segura
sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Register : AppScreens("register")
    object ArbitrosList : AppScreens("arbitros_list")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Creamos el NavController aquí

    // NavHost es el encargado de gestionar qué pantalla se muestra
    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route // La app empieza en la pantalla de Login
    ) {
        // --- PANTALLA DE LOGIN ---
        composable(AppScreens.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    // Si el login es exitoso, navegamos a la lista de árbitros
                    // y limpiamos la pila de navegación para que no pueda volver atrás.
                    navController.navigate(AppScreens.ArbitrosList.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    // Navegamos a la pantalla de registro
                    navController.navigate(AppScreens.Register.route)
                }
            )
        }

        // --- PANTALLA DE REGISTRO (CORREGIDA) ---
        composable(AppScreens.Register.route) {
            // A la pantalla de Registro le pasamos las acciones de navegación que ahora espera
            RegisterScreen(
                onRegisterSuccess = {
                    // Cuando el registro sea exitoso (gestionado por el ViewModel),
                    // navegamos a la lista de árbitros y limpiamos la pila.
                    navController.navigate(AppScreens.ArbitrosList.route) {
                        // Limpiamos hasta la pantalla de login para que no pueda volver
                        // ni al registro ni al login.
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    // Vuelve a la pantalla anterior (Login)
                    navController.popBackStack()
                }
            )
        }

        // --- PANTALLA PRINCIPAL (LISTA DE ÁRBITROS) ---
        composable(AppScreens.ArbitrosList.route) {
            ArbitroListScreen()
        }
    }
}
