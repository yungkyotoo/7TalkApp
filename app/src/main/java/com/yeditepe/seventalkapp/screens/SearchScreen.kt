package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    currentUserName: String,
    currentUserAvatar: Int
) {
    var searchText by remember { mutableStateOf("") }

    // Resimdeki listeyi buraya ekledim
    val hashtags = listOf(
        "#vcd",
        "#yemekhane",
        "#etkinlik",
        "#yurt",
        "#not",
        "#rektorluk",
        "#ikinciel",
        "#oyun",
        "#camlık",
        "#ikinciel",
        "#oyun",
        "#kırmızı",
        "#mavi"
    )

    // Arama yapınca filtrelemesi için
    val filteredList = if (searchText.isEmpty()) {
        hashtags
    } else {
        hashtags.filter { it.contains(searchText, ignoreCase = true) }
    }

    // Scaffold kullanmıyoruz (Çift alt bar olmasın diye)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Arama Çubuğu (Mavi çerçeveli)
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ara...", color = Color.Gray) },
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Ara", tint = Color.Black)
            },
            shape = RoundedCornerShape(50), // Kenarları yuvarlak
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2), // Mavi çerçeve
                unfocusedBorderColor = Color(0xFF1976D2),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Liste (Resimdeki gibi ortalı ve çizgili)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(filteredList) { tag ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Tıklayınca arama kutusuna yazsın istersen:
                                searchText = tag
                            }
                            .padding(vertical = 16.dp), // Satır yüksekliği
                        contentAlignment = Alignment.Center // Yazıyı ortala
                    ) {
                        Text(
                            text = tag,
                            fontSize = 18.sp,
                            color = Color(0xFF424242),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Altındaki gri çizgi
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFE0E0E0)
                    )
                }
            }
        }
    }
}