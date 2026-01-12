package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yeditepe.seventalkapp.R

// --- VERİ MODELLERİ ---
data class SearchItem(
    val title: String,
    val type: SearchType,
    val id: Int? = null // Kulüpler için ID (Basketbol = 1)
)

enum class SearchType { TAG, CLUB }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    currentUserName: String,
    currentUserAvatar: Int
) {
    // Arama metni
    var searchQuery by remember { mutableStateOf("") }

    // --- 1. VERİLERİ HAZIRLA ---

    // Etiketler
    val tagList = listOf(
        "#vcd", "#yemekhane", "#etkinlik", "#yurt", "#not",
        "#rektorluk", "#ikinciel", "#oyun", "#camlık", "#vizeler"
    ).map { SearchItem(it, SearchType.TAG) }

    // Kulüpler (ID'LERİ ÖNEMLİ)
    val clubList = listOf(
        SearchItem("Basketbol Kulübü", SearchType.CLUB, id = 1), // ID = 1 Basketbol Sayfasına Gider
        SearchItem("Dans Kulübü", SearchType.CLUB, id = 2),
        SearchItem("Dağcılık Kulübü", SearchType.CLUB, id = 3),
        SearchItem("E-Spor Kulübü", SearchType.CLUB, id = 4),
        SearchItem("Tiyatro Kulübü", SearchType.CLUB, id = 5)
    )

    // Listeleri birleştir
    val allItems = clubList + tagList

    // --- 2. FİLTRELEME ---
    val filteredItems = if (searchQuery.isEmpty()) {
        allItems
    } else {
        allItems.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    placeholder = { Text("Ara (Örn: Basketbol)", color = Color.Gray) },
                    trailingIcon = { Icon(Icons.Default.Search, "Ara", tint = Color.Black) },
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(0xFF1976D2),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
                )
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Ana Sayfa") },
                    selected = false,
                    onClick = {
                        navController.navigate("home/$currentUserName/$currentUserAvatar") {
                            popUpTo("home/$currentUserName/$currentUserAvatar") { inclusive = true }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD))
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, null) },
                    label = { Text("Ara") },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD))
                )
                NavigationBarItem(icon = { Icon(Icons.Default.AddCircle, null) }, label = { Text("Başlık Aç") }, selected = false, onClick = {})
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Topluluk") }, selected = false, onClick = {})
                NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Favoriler") }, selected = false, onClick = {})
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (filteredItems.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                        Text("Sonuç bulunamadı :(", color = Color.Gray)
                    }
                }
            }

            items(filteredItems) { item ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // --- YÖNLENDİRME MANTIĞI BURADA ---
                                if (item.type == SearchType.CLUB && item.id != null) {
                                    // Eğer Basketbol ise (id=1), club_detail/1 rotasına gider
                                    navController.navigate("club_detail/${item.id}")
                                } else {
                                    println("${item.title} tıklandı (Etiket)")
                                }
                            }
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // İkon Seçimi (Kulüp -> İnsan, Etiket -> Liste)
                        Icon(
                            imageVector = if (item.type == SearchType.CLUB) Icons.Default.Person else Icons.Default.List,
                            contentDescription = null,
                            tint = if (item.type == SearchType.CLUB) Color(0xFF1976D2) else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            color = if (item.type == SearchType.CLUB) Color.Black else Color(0xFF616161)
                        )
                    }
                    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                }
            }
        }
    }
}