package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.yeditepe.seventalkapp.R
import com.yeditepe.seventalkapp.viewmodel.ClubsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
    userAvatar: Int,
    viewModel: ClubsViewModel = viewModel(),
    onClubClick: (Int) -> Unit
) {
    val clubs by viewModel.clubs.collectAsState()
    var selectedItem by remember { mutableStateOf(0) }
    val brandBlue = Color(0xFF1976D2)

    Scaffold(

        topBar = {
            TopAppBar(

                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menü",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.img_7alkhomescreen),
                        contentDescription = "7ALK Home Logo",
                        modifier = Modifier
                            .height(250.dp)
                            .wrapContentWidth(Alignment.Start),
                        contentScale = ContentScale.Fit
                    )
                },
                actions = {

                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) { Text("1") }
                        },
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Bildirimler",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Kullanıcının Profil Resmi
                    Image(
                        painter = painterResource(id = userAvatar),
                        contentDescription = "Profil",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE0B2))
                            .border(1.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                },
                // Arka Plan Mavi
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brandBlue)
            )
        },
        // --- ALT MENÜ ---
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Ana Sayfa") }, selected = selectedItem == 0, onClick = { selectedItem = 0 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                NavigationBarItem(icon = { Icon(Icons.Default.Search, null) }, label = { Text("Ara") }, selected = selectedItem == 1, onClick = { selectedItem = 1 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                NavigationBarItem(icon = { Icon(Icons.Default.AddCircle, null, modifier = Modifier.size(32.dp), tint = Color.Gray) }, label = { Text("Başlık Aç") }, selected = selectedItem == 2, onClick = { selectedItem = 2 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Topluluk") }, selected = selectedItem == 3, onClick = { selectedItem = 3 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Favoriler") }, selected = selectedItem == 4, onClick = { selectedItem = 4 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
            }
        }
    ) { paddingValues ->
        // --- SAYFA İÇERİĞİ ---
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
            .padding(16.dp))
        {
            item {
                Text(text = "Merhaba $userName!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Önerilen Topluluklar", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            }
            // Yatay Kulüp Listesi
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(clubs) { club ->
                        ClubCardHorizontal(club.name, club.imageUrl) { onClubClick(club.id) }
                    }
                }
            }
            // Butonlar (Gündem / Keşfet)
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Yeşil Buton
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) { Text("Gündem") }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) { Text("Keşfet", color = Color.Gray) }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Gündem Listesi
            val gundemListesi = listOf("mutlu olmayı becerenler" to "42", "final haftası" to "11", "seçmeli ders seçimi" to "21", "ring saat değişikliği" to "29", "erasmus almanya" to "14", "kariyer günleri" to "3", "kampüs köpekleri" to "21")
            items(gundemListesi) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(it.first, fontSize = 16.sp, color = Color.Gray)
                    Text(it.second, fontSize = 16.sp, color = Color.Gray)
                }
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            }
        }
    }
}

// Yatay Kart Tasarımı
@Composable
fun ClubCardHorizontal(name: String, imageUrl: String?, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.size(width = 140.dp, height = 160.dp)) {
        Column {
            Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color.White), contentAlignment = Alignment.Center) {
                if (imageUrl != null) AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.size(80.dp))
            }
            Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF1976D2)).padding(8.dp), contentAlignment = Alignment.Center) {
                Text(name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            }
        }
    }
}