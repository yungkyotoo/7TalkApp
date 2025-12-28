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

                // Başlangıç rotası: LOGIN
                NavHost(navController = navController, startDestination = "login") {

                    // 1. GİRİŞ EKRANI
                    composable("login") {
                        LoginScreen(
                            onRegisterClick = {
                                navController.navigate("register")
                            },
                            onLoginClick = {
                                // Login butonuna basınca varsayılan bir isimle gitsin
                                navController.navigate("home/Ahmet Töz") {
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
                            // Kayıt başarılı olunca girilen ismi alıp Home'a yolluyoruz
                            onRegisterSuccess = { fullName ->
                                navController.navigate("home/$fullName") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 3. ANA EKRAN (Parametre alıyor: {userName})
                    composable(
                        route = "home/{userName}",
                        arguments = listOf(navArgument("userName") { type = NavType.StringType })
                    ) { backStackEntry ->

                        // Gelen ismi paket içinden çıkarıyoruz
                        val incomingName = backStackEntry.arguments?.getString("userName") ?: "Misafir"

                        HomeScreen(
                            userName = incomingName, // İsmi ekrana veriyoruz
                            onClubClick = { id ->
                                println("Tıklanan Kulüp ID: $id")
                            }
                        )
                    }
                }
            }
        }
    }
}