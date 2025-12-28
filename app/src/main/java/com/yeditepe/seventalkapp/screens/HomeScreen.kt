package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yeditepe.seventalkapp.R
import com.yeditepe.seventalkapp.viewmodel.ClubsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String, // İsim verisi buradan geliyor
    viewModel: ClubsViewModel = viewModel(),
    onClubClick: (Int) -> Unit
) {
    val clubs by viewModel.clubs.collectAsState()
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Logo Kullanımı (Yazı Yerine)
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = "7ALK Logo",
                        modifier = Modifier
                            .height(200.dp)
                            .wrapContentWidth(Alignment.Start)
                    )
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Notifications, contentDescription = "Bildirimler", tint = Color.White) }
                    Box(modifier = Modifier.padding(end = 16.dp).size(36.dp).clip(CircleShape).background(Color.LightGray), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, contentDescription = "Ana Sayfa") }, label = { Text("Ana Sayfa") }, selected = selectedItem == 0, onClick = { selectedItem = 0 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                NavigationBarItem(icon = { Icon(Icons.Default.Search, contentDescription = "Ara") }, label = { Text("Ara") }, selected = selectedItem == 1, onClick = { selectedItem = 1 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                NavigationBarItem(icon = { Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Başlık Aç", modifier = Modifier.size(32.dp), tint = Color.Gray) }, label = { Text("Başlık Aç") }, selected = selectedItem == 2, onClick = { selectedItem = 2 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                // Hata veren "Group" yerine "Person" veya "List" kullanıyoruz
                NavigationBarItem(icon = { Icon(Icons.Default.Person, contentDescription = "Topluluk") }, label = { Text("Topluluk") }, selected = selectedItem == 3, onClick = { selectedItem = 3 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                NavigationBarItem(icon = { Icon(Icons.Default.Star, contentDescription = "Favoriler") }, label = { Text("Favoriler") }, selected = selectedItem == 4, onClick = { selectedItem = 4 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            item {
                Text(text = "Merhaba $userName!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Önerilen Topluluklar", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(clubs) { club ->
                        ClubCardHorizontal(club.name, club.imageUrl) { onClubClick(club.id) }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) { Text("Gündem") }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = {}, shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) { Text("Keşfet", color = Color.Gray) }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            val gundemListesi = listOf("mutlu olmayı becerenler" to "42", "final haftası" to "11", "seçmeli ders seçimi" to "21", "ring saat değişikliği" to "29", "erasmus almanya" to "14")
            items(gundemListesi) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(it.first, fontSize = 16.sp, color = Color.Gray)
                    Text(it.second, fontSize = 16.sp, color = Color.Gray)
                }
                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }
    }
}

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