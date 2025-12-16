package com.example.a7talkapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star // <-- Bu yeni eklendi
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    // --- Alt Menüdekiler ---
    object Home : Screen("home", "Ana Sayfa", Icons.Filled.Home)
    object Search : Screen("search", "Ara", Icons.Filled.Search)
    object Create : Screen("create", "Başlık Aç", Icons.Filled.AddCircle)
    object Community : Screen("community", "Topluluk", Icons.Filled.Group)
    object Favorites : Screen("favorites", "Favoriler", Icons.Filled.Favorite)

    // --- Profil ve Ayarlar ---
    object Profile : Screen("profile", "Profilim", Icons.Filled.Person)
    object EditProfile : Screen("edit_profile", "Düzenle", Icons.Filled.Edit)
    object Settings : Screen("settings", "Ayarlar", Icons.Filled.Settings)

    // --- YENİ EKLENEN: Karşılama Ekranı ---
    object Onboarding : Screen("onboarding", "İlgi Alanları", Icons.Filled.Star)
}