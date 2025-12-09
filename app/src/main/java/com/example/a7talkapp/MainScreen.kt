package com.example.a7talkapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        // Sayfaların değiştiği alan burası
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { PlaceholderScreen("Ana Sayfa Akışı") }
            composable(Screen.Search.route) { PlaceholderScreen("Arama Ekranı") }
            composable(Screen.Create.route) { PlaceholderScreen("Başlık Açma Ekranı") }
            composable(Screen.Community.route) { PlaceholderScreen("Topluluklar") }
            composable(Screen.Favorites.route) { PlaceholderScreen("Favoriler") }
        }
    }
}

// Şimdilik sayfalar boş kalmasın diye geçici ekran tasarımı
@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}