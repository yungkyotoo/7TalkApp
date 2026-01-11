package com.yeditepe.seventalkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yeditepe.seventalkapp.screens.HomeScreen
import com.yeditepe.seventalkapp.screens.LoginScreen
import com.yeditepe.seventalkapp.screens.RegisterScreen
import com.yeditepe.seventalkapp.ui.theme._7TalkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _7TalkAppTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {

                    // 1. LOGIN EKRANI
                    composable("login") {
                        LoginScreen(
                            onRegisterClick = {
                                navController.navigate("register")
                            },
                            onLoginSuccess = { userName ->
                                navController.navigate("home/$userName/${R.drawable.ppgroup1}") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 2. KAYIT EKRANI
                    composable("register") {
                        RegisterScreen(
                            onLoginClick = {
                                navController.popBackStack()
                            },
                            onRegisterSuccess = { fullName, avatarId ->
                                navController.navigate("home/$fullName/$avatarId") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 3. ANA EKRAN
                    composable(
                        route = "home/{userName}/{avatarId}",
                        arguments = listOf(
                            navArgument("userName") { type = NavType.StringType },
                            navArgument("avatarId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val incomingName = backStackEntry.arguments?.getString("userName") ?: "Misafir"
                        val incomingAvatar = backStackEntry.arguments?.getInt("avatarId") ?: R.drawable.ppgroup1

                        HomeScreen(
                            userName = incomingName,
                            userAvatar = incomingAvatar,
                            onClubClick = { id -> println("Tıklanan Kulüp ID: $id") }
                        )
                    }
                }
            }
        }
    }
}