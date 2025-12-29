package com.gokbe.yeditalk.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text // placeholder for new screens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gokbe.yeditalk.ui.components.BottomNavItem
import com.gokbe.yeditalk.ui.components.BottomNavigationBar
import com.gokbe.yeditalk.ui.screens.content.HavuzScreen
import com.gokbe.yeditalk.ui.screens.content.StajArayanlarScreen
import com.gokbe.yeditalk.ui.screens.onboarding.OnboardingScreen
import com.gokbe.yeditalk.ui.screens.profile.EditProfileScreen
import com.gokbe.yeditalk.ui.screens.profile.MyProfileScreen
import com.gokbe.yeditalk.ui.screens.settings.AccountSettingsScreen
import com.gokbe.yeditalk.ui.components.TopBar
import androidx.hilt.navigation.compose.hiltViewModel
import com.gokbe.yeditalk.ui.screens.profile.ProfileViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.gokbe.yeditalk.ui.components.AvatarSelectionDialog

@Composable
fun YediTalkNavigation(
    navController: NavHostController = rememberNavController(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val user by profileViewModel.user.collectAsState()

    
    // Show BottomBar only on main screens
    val showBottomBar = currentRoute in listOf(
        BottomNavItem.AnaSayfa.route,
        BottomNavItem.Ara.route,
        BottomNavItem.BaslikAc.route,
        BottomNavItem.Topluluk.route,
        BottomNavItem.Favoriler.route
    )



    Scaffold(
        topBar = {
            if (showBottomBar) {
                TopBar(
                    currentAvatarId = user.avatarId,
                    onMenuClick = { /* TODO: Open drawer */ },
                    onProfileClick = { navController.navigate("profile") },
                    onNotificationClick = { /* Handle notifications */ }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "onboarding", 
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("onboarding") {
                OnboardingScreen(
                    onOnboardingComplete = {
                        navController.navigate(BottomNavItem.AnaSayfa.route) {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                )
            }
            
            composable(BottomNavItem.AnaSayfa.route) {
                // Was Havuz, mapping Ana Sayfa to HavuzScreen for now or create new
                HavuzScreen() 
            }
            
            composable(BottomNavItem.Ara.route) {
                // Was Staj, mapping Ara to StajArayanlarScreen for now or placeholder
                StajArayanlarScreen()
            }
            
            composable(BottomNavItem.BaslikAc.route) {
                // Placeholder
               Text("Başlık Aç Ekranı")
            }
            
            composable(BottomNavItem.Topluluk.route) {
                // Placeholder
                Text("Topluluk Ekranı")
            }
            
            composable(BottomNavItem.Favoriler.route) {
                // Placeholder
                Text("Favoriler Ekranı")
            }
            
            // Keep Profile route accessible via other means if needed, 
            // or map one of the tabs to it?
            // The user removed "Profile" from bottom bar. 
            // It might be accessed via a header icon or one of the tabs. 
            // For now I'll keep the route definition but it's not in the bottom bar.
            
            composable("profile") {
                 MyProfileScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToSettings = { navController.navigate("settings") },
                    onNavigateToEditProfile = { navController.navigate("edit_profile") }
                )
            }
            
            composable("settings") {
                AccountSettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onLogout = { 
                        navController.navigate("onboarding") {
                            popUpTo(0) 
                        }
                    },
                    onDeleteAccount = {
                        navController.navigate("onboarding") {
                            popUpTo(0)
                        }
                    }
                )
            }
            
            composable("edit_profile") {
                EditProfileScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
