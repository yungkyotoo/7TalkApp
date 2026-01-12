package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeditepe.seventalkapp.data.model.PostManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    currentUserName: String,
    currentUserAvatar: Int,
    onBackClick: () -> Unit
) {
    val brandBlue = Color(0xFF1976D2)

    // Sadece isFavorite == true olanları filtrele
    val favoritePosts = PostManager.posts.filter { it.isFavorite }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri", tint = Color.White)
                    }
                },
                title = { Text("Favorilerim", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brandBlue)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            if (favoritePosts.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp), contentAlignment = Alignment.Center) {
                        Text("Henüz favorilenmiş bir gönderi yok.", color = Color.Gray)
                    }
                }
            }

            items(favoritePosts) { post ->
                // Burada da PostItem kullanıyoruz, böylece listeden de çıkarılabilir
                PostItem(
                    post = post,
                    userName = currentUserName, // Kendi profilimizdeymiş gibi gösteriyoruz (istersen gönderiyi atanın adını da tutabilirsin)
                    userAvatar = currentUserAvatar,
                    onFavoriteClick = { clickedPost ->
                        PostManager.toggleFavorite(clickedPost)
                    }
                )
            }
        }
    }
}