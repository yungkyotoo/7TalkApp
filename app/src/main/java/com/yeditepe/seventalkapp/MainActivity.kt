package com.yeditepe.seventalkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yeditepe.seventalkapp.screens.ContactScreen
import com.yeditepe.seventalkapp.screens.ContractsScreen // YENİ IMPORT
import com.yeditepe.seventalkapp.screens.CreatePostScreen
import com.yeditepe.seventalkapp.screens.FaqScreen
import com.yeditepe.seventalkapp.screens.HomeScreen
import com.yeditepe.seventalkapp.screens.LoginScreen
import com.yeditepe.seventalkapp.screens.ProfileScreen
import com.yeditepe.seventalkapp.screens.RegisterScreen
import com.yeditepe.seventalkapp.screens.SearchScreen
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

                    // 2. REGISTER
                    composable("register") {
                        RegisterScreen(
                            onLoginClick = { navController.popBackStack() },
                            onRegisterSuccess = { fullName, avatarId ->
                                navController.navigate("home/$fullName/$avatarId") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 3. HOME
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
                            onClubClick = { id -> println("Kulüp ID: $id") },
                            onProfileClick = { navController.navigate("profile/$incomingName/$incomingAvatar") },
                            onSearchClick = { navController.navigate("search/$incomingName/$incomingAvatar") },
                            onAddClick = { navController.navigate("create_post/$incomingName/$incomingAvatar") },
                            onFaqClick = { navController.navigate("faq") },
                            onContactClick = { navController.navigate("contact") },
                            onContractsClick = { navController.navigate("contracts") } // <--- YENİ BAĞLANTI
                        )
                    }

                    // 4. PROFILE
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
                            onBackClick = { navController.popBackStack() }
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

                    // 9. SÖZLEŞMELER (CONTRACTS) - YENİ
                    composable("contracts") {
                        ContractsScreen(onBackClick = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}