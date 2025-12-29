package com.gokbe.yeditalk.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gokbe.yeditalk.ui.theme.BlueYeditepe
import com.gokbe.yeditalk.ui.theme.GreenYeditepe

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object AnaSayfa : BottomNavItem("anasayfa", Icons.Default.Home, "Ana Sayfa")
    object Ara : BottomNavItem("ara", Icons.Default.Search, "Ara")
    object BaslikAc : BottomNavItem("baslik_ac", Icons.Default.AddCircle, "Başlık Aç")
    object Topluluk : BottomNavItem("topluluk", Icons.Default.Person, "Topluluk")
    object Favoriler : BottomNavItem("favoriler", Icons.Default.Star, "Favoriler")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.AnaSayfa,
        BottomNavItem.Ara,
        BottomNavItem.BaslikAc,
        BottomNavItem.Topluluk,
        BottomNavItem.Favoriler
    )
    
    NavigationBar(
        containerColor = Color.White,
        contentColor = BlueYeditepe
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 10.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BlueYeditepe, // Changed to Blue based on image (Topluluk is blue)
                    selectedTextColor = BlueYeditepe,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
