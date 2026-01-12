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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    currentUserName: String, // Alt bardan home'a dönerken lazım olacak
    currentUserAvatar: Int   // Alt bardan home'a dönerken lazım olacak
) {
    // --- ARAMA MANTIĞI VE VERİLER ---

    // 1. Arama çubuğuna yazılan yazı (State)
    var searchQuery by remember { mutableStateOf("") }

    // 2. Tüm Etiket Listesi (Veritabanı gibi düşün)
    val allTags = listOf(
        "#vcd", "#yemekhane", "#etkinlik", "#yurt", "#not",
        "#rektorluk", "#ikinciel", "#oyun", "#camlık", "#kırmızı",
        "#mavi", "#vizeler", "#finaller", "#festival", "#kampus",
        "#kedi", "#köpek", "#muhendislik", "#mimarlık", "#tıp"
    )

    // 3. Filtrelenmiş Liste (Ekranda gösterilecek olan)
    // Eğer arama kutusu boşsa hepsini göster, doluysa içinde geçeni göster
    val filteredTags = if (searchQuery.isEmpty()) {
        allTags
    } else {
        allTags.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        // --- ÜST KISIM (ARAMA ÇUBUĞU) ---
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                // Görseldeki gibi Yuvarlak Kenarlı Arama Çubuğu
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), // Standart yükseklik
                    placeholder = { Text("Ara...", color = Color.Gray) },
                    trailingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Ara", tint = Color.Black)
                    },
                    shape = RoundedCornerShape(50), // Tam yuvarlak kenarlar (Hap şekli)
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2), // Tıklanınca Mavi (Logonun rengi)
                        unfocusedBorderColor = Color(0xFF1976D2), // Tıklanmayınca da Mavi çerçeve
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
                )
            }
        },
        // --- ALT MENÜ (NAVIGASYON) ---
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                // Home Butonu (Tıklayınca Ana Sayfaya Döner)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Ana Sayfa") },
                    selected = false,
                    onClick = {
                        // Home'a geri dön (Stack'i temizleyerek)
                        navController.navigate("home/$currentUserName/$currentUserAvatar") {
                            popUpTo("home/$currentUserName/$currentUserAvatar") { inclusive = true }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD))
                )
                // Ara Butonu (Şu an buradayız, o yüzden selected = true)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, null) },
                    label = { Text("Ara") },
                    selected = true, // Seçili olduğu belli olsun
                    onClick = { /* Zaten buradayız */ },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD))
                )
                NavigationBarItem(icon = { Icon(Icons.Default.AddCircle, null) }, label = { Text("Başlık Aç") }, selected = false, onClick = {})
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Topluluk") }, selected = false, onClick = {})
                NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Favoriler") }, selected = false, onClick = {})
            }
        }
    ) { paddingValues ->
        // --- LİSTE KISMI ---
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp) // Kenarlardan boşluk
        ) {
            // Eğer arama sonucu boşsa kullanıcıya bilgi verelim
            if (filteredTags.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                        Text("Sonuç bulunamadı :(", color = Color.Gray)
                    }
                }
            }

            // Listeyi Ekrana Basma
            items(filteredTags) { tag ->
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp) // Görseldeki gibi geniş aralık
                            .clickable {
                                // Buraya tıklayınca ne olacağını yazabilirsin (İlerde detay sayfasına gider)
                                println("$tag tıklandı")
                            },
                        contentAlignment = Alignment.Center // Yazıyı ortala
                    ) {
                        Text(
                            text = tag,
                            fontSize = 18.sp,
                            color = Color(0xFF616161) // Koyu gri tonu
                        )
                    }
                    // Her elemanın altına ince çizgi (Divider)
                    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                }
            }
        }
    }
}

