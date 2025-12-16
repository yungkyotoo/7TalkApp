package com.example.a7talkapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState // <-- Bu kütüphane eklendi
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // --- Hangi ekranda olduğumuzu takip edelim ---
    // (Onboarding ekranındaysak menüleri gizlemek için gerekli)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // --- Profil Verisi (State) ---
    var userProfile by remember {
        mutableStateOf(
            UserProfile(
                name = "Ahmet Töz",
                department = "Bilgisayar Mühendisliği | Topluluk Kurucusu",
                bio = "Hello world!",
                followers = "1.2B",
                following = "350",
                interests = listOf("Not", "Kahve", "Spor", "Satranç", "Eğlence")
            )
        )
    }

    Scaffold(
        // Üst Bar (Sadece Onboarding ekranında değilsek göster)
        topBar = {
            if (currentRoute != Screen.Onboarding.route) {
                CenterAlignedTopAppBar(
                    title = { Text("7TALK", color = Color(0xFF2244CC)) },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "Profilim", tint = Color.Gray)
                        }
                        IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                            Icon(Icons.Default.Settings, contentDescription = "Ayarlar", tint = Color.Gray)
                        }
                    }
                )
            }
        },
        // Alt Menü (Sadece Onboarding ekranında değilsek göster)
        bottomBar = {
            if (currentRoute != Screen.Onboarding.route) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route, // <-- BAŞLANGIÇ EKRANI ARTIK ONBOARDING
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- Giriş / Onboarding (YENİ EKLENEN) ---
            composable(Screen.Onboarding.route) {
                OnboardingScreen(navController = navController)
            }

            // --- Ana Sekmeler ---
            composable(Screen.Home.route) { PlaceholderScreen("Ana Sayfa Akışı") }
            composable(Screen.Search.route) { PlaceholderScreen("Arama Ekranı") }
            composable(Screen.Create.route) { PlaceholderScreen("Başlık Açma") }
            composable(Screen.Community.route) { PlaceholderScreen("Topluluklar") }
            composable(Screen.Favorites.route) { PlaceholderScreen("Favoriler") }

            // --- Profil Ekranı ---
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController, userProfile = userProfile)
            }

            // --- Profili Düzenle Ekranı ---
            composable(Screen.EditProfile.route) {
                EditProfileScreen(
                    navController = navController,
                    currentProfile = userProfile,
                    onSave = { newName, newBio ->
                        userProfile = userProfile.copy(name = newName, bio = newBio)
                    }
                )
            }

            // --- Ayarlar Sayfası ---
            composable(Screen.Settings.route) {
                SettingsScreen(navController = navController)
            }
        }
    }
}

// Placeholder (Değişmedi)
@Composable
fun PlaceholderScreen(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}