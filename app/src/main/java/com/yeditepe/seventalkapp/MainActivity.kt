package com.yeditepe.seventalkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yeditepe.seventalkapp.screens.*
import com.yeditepe.seventalkapp.ui.theme._7TalkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _7TalkAppTheme {
                val navController = rememberNavController()
                // startDestination 'login' olmalı, test için 'home' yapma
                NavHost(navController = navController, startDestination = "login") {

                    composable("login") {
                        LoginScreen(
                            onRegisterClick = { navController.navigate("register") },
                            onLoginSuccess = { userName -> navController.navigate("home/$userName/${R.drawable.ppgroup1}") { popUpTo("login") { inclusive = true } } }
                        )
                    }

                    composable("register") {
                        RegisterScreen(
                            onLoginClick = { navController.popBackStack() },
                            onRegisterSuccess = { fullName, avatarId -> navController.navigate("home/$fullName/$avatarId") { popUpTo("login") { inclusive = true } } }
                        )
                    }

                    composable(
                        route = "home/{userName}/{avatarId}",
                        arguments = listOf(navArgument("userName") { type = NavType.StringType }, navArgument("avatarId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val incomingName = backStackEntry.arguments?.getString("userName") ?: "Misafir"
                        val incomingAvatar = backStackEntry.arguments?.getInt("avatarId") ?: R.drawable.ppgroup1

                        // GÜNCELLENMİŞ ÇAĞIRMA: navController'ı buraya ekledik
                        HomeScreen(
                            navController = navController, // <--- BURASI ÇOK ÖNEMLİ
                            userName = incomingName,
                            userAvatar = incomingAvatar,
                            onClubClick = { clubId -> navController.navigate("club_detail/$clubId") },
                            onProfileClick = { navController.navigate("profile/$incomingName/$incomingAvatar") },
                            onFaqClick = { navController.navigate("faq") },
                            onContactClick = { navController.navigate("contact") },
                            onContractsClick = { navController.navigate("contracts") },
                            onSettingsClick = { navController.navigate("settings") }
                        )
                    }

                    composable(
                        route = "profile/{userName}/{avatarId}",
                        arguments = listOf(navArgument("userName") { type = NavType.StringType }, navArgument("avatarId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val incomingName = backStackEntry.arguments?.getString("userName") ?: "Kullanıcı"
                        val incomingAvatar = backStackEntry.arguments?.getInt("avatarId") ?: R.drawable.ppgroup1
                        ProfileScreen(
                            userName = incomingName,
                            userAvatar = incomingAvatar,
                            onBackClick = { navController.popBackStack() },
                            onSidebarItemClick = { item ->
                                when (item) {
                                    "SSS" -> navController.navigate("faq")
                                    "Contact" -> navController.navigate("contact")
                                    "Contracts" -> navController.navigate("contracts")
                                    "Ayarlar" -> navController.navigate("settings")
                                    "Club_1" -> navController.navigate("club_detail/1")
                                    "Club_2" -> navController.navigate("club_detail/2")
                                    "Club_3" -> navController.navigate("club_detail/3")
                                }
                            }
                        )
                    }

                    // Diğer rotalar (SearchScreen ve CreatePostScreen artık HomeScreen'in içinde yönetiliyor ama rota olarak da kalabilir, zararı yok)
                    composable("faq") { FaqScreen(onBackClick = { navController.popBackStack() }) }
                    composable("contact") { ContactScreen(onBackClick = { navController.popBackStack() }) }
                    composable("contracts") { ContractsScreen(onBackClick = { navController.popBackStack() }) }
                    composable("settings") { SettingsScreen(onBackClick = { navController.popBackStack() }, onLogout = { navController.navigate("login") { popUpTo(0) { inclusive = true } } }) }

                    composable("club_detail/{clubId}", arguments = listOf(navArgument("clubId") { type = NavType.IntType })) { backStackEntry ->
                        val clubId = backStackEntry.arguments?.getInt("clubId") ?: 0
                        ClubDetailScreen(clubId = clubId, onBackClick = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}