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

                NavHost(navController = navController, startDestination = "login") {

                    // 1. LOGIN
                    composable("login") {
                        LoginScreen(
                            onRegisterClick = { navController.navigate("register") },
                            onLoginSuccess = { userName ->
                                navController.navigate("home/$userName/${R.drawable.ppgroup1}") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 2. REGISTER (DÜZELTİLDİ: Direkt Home'a gidiyor)
                    composable("register") {
                        RegisterScreen(
                            onLoginClick = { navController.popBackStack() },
                            onRegisterSuccess = { fullName, avatarId ->
                                // interest_selection yerine direkt home'a yönlendiriyoruz
                                navController.navigate("home/$fullName/$avatarId") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 3. HOME (DÜZELTİLDİ: interests parametresi kaldırıldı)
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
                            onClubClick = { clubId ->
                                navController.navigate("club_detail/$clubId")
                            },
                            // Sadece isim ve avatar ile gidiyoruz
                            onProfileClick = { navController.navigate("profile/$incomingName/$incomingAvatar") },
                            onSearchClick = { navController.navigate("search/$incomingName/$incomingAvatar") },
                            onAddClick = { navController.navigate("create_post/$incomingName/$incomingAvatar") },
                            onFaqClick = { navController.navigate("faq") },
                            onContactClick = { navController.navigate("contact") },
                            onContractsClick = { navController.navigate("contracts") }
                        )
                    }

                    // 4. PROFİL EKRANI
                    composable(
                        route = "profile/{userName}/{avatarId}",
                        arguments = listOf(
                            navArgument("userName") { type = NavType.StringType },
                            navArgument("avatarId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val incomingName = backStackEntry.arguments?.getString("userName") ?: "Kullanıcı"
                        val incomingAvatar = backStackEntry.arguments?.getInt("avatarId") ?: R.drawable.ppgroup1

                        ProfileScreen(
                            userName = incomingName,
                            userAvatar = incomingAvatar,
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onSidebarItemClick = { item ->
                                when (item) {
                                    "SSS" -> navController.navigate("faq")
                                    "Contact" -> navController.navigate("contact")
                                    "Contracts" -> navController.navigate("contracts")
                                    "Club_1" -> navController.navigate("club_detail/1")
                                    "Club_2" -> navController.navigate("club_detail/2")
                                    "Club_3" -> navController.navigate("club_detail/3")
                                    "Ayarlar" -> { /* Ayarlar sayfası rotası eklenebilir */ }
                                    else -> println("$item tıklandı")
                                }
                            }
                        )
                    }

                    // 5. SEARCH
                    composable(
                        route = "search/{userName}/{avatarId}",
                        arguments = listOf(
                            navArgument("userName") { type = NavType.StringType },
                            navArgument("avatarId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val incomingName = backStackEntry.arguments?.getString("userName") ?: "Kullanıcı"
                        val incomingAvatar = backStackEntry.arguments?.getInt("avatarId") ?: R.drawable.ppgroup1

                        SearchScreen(
                            navController = navController,
                            currentUserName = incomingName,
                            currentUserAvatar = incomingAvatar
                        )
                    }

                    // 6. CREATE POST
                    composable(
                        route = "create_post/{userName}/{avatarId}",
                        arguments = listOf(
                            navArgument("userName") { type = NavType.StringType },
                            navArgument("avatarId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val incomingName = backStackEntry.arguments?.getString("userName") ?: "Kullanıcı"
                        val incomingAvatar = backStackEntry.arguments?.getInt("avatarId") ?: R.drawable.ppgroup1

                        CreatePostScreen(
                            navController = navController,
                            userName = incomingName,
                            userAvatar = incomingAvatar
                        )
                    }

                    // 7. SSS (FAQ)
                    composable("faq") {
                        FaqScreen(onBackClick = { navController.popBackStack() })
                    }

                    // 8. İLETİŞİM (CONTACT)
                    composable("contact") {
                        ContactScreen(onBackClick = { navController.popBackStack() })
                    }

                    // 9. SÖZLEŞMELER (CONTRACTS)
                    composable("contracts") {
                        ContractsScreen(onBackClick = { navController.popBackStack() })
                    }

                    // 10. KULÜP DETAY EKRANI
                    composable(
                        route = "club_detail/{clubId}",
                        arguments = listOf(navArgument("clubId") { type = NavType.IntType })
                    ) {
                        val clubId = it.arguments?.getInt("clubId") ?: 0
                        ClubDetailScreen(
                            clubId = clubId,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}