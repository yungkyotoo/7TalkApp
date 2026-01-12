package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import com.yeditepe.seventalkapp.R
import com.yeditepe.seventalkapp.viewmodel.ClubsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, // <--- BURASI EKLENDİ (Null hatasını bu çözecek)
    userName: String,
    userAvatar: Int,
    viewModel: ClubsViewModel = viewModel(),
    onClubClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onFaqClick: () -> Unit,
    onContactClick: () -> Unit,
    onContractsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val brandBlue = Color(0xFF1976D2)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = brandBlue, drawerContentColor = Color.White, modifier = Modifier.width(300.dp)) {
                SidebarContent(onMenuItemClick = { item ->
                    scope.launch { drawerState.close() }
                    when (item) {
                        "SSS" -> onFaqClick()
                        "Contact" -> onContactClick()
                        "Contracts" -> onContractsClick()
                        "Ayarlar" -> onSettingsClick()
                        "Club_1" -> onClubClick(1)
                        "Club_2" -> onClubClick(2)
                        "Club_3" -> onClubClick(3)
                    }
                })
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (selectedItem == 0) {
                    TopAppBar(
                        navigationIcon = { IconButton(onClick = { scope.launch { drawerState.open() } }) { Icon(Icons.Default.Menu, "Menü", tint = Color.White, modifier = Modifier.size(32.dp)) } },
                        title = { Image(painter = painterResource(id = R.drawable.img_7alkhomescreen), contentDescription = "Logo", modifier = Modifier.height(28.dp).wrapContentWidth(Alignment.Start), contentScale = ContentScale.Fit) },
                        actions = {
                            BadgedBox(badge = { Badge(containerColor = Color.Red, contentColor = Color.White) { Text("1") } }, modifier = Modifier.padding(end = 12.dp)) { Icon(Icons.Default.Notifications, "Bildirimler", tint = Color.White, modifier = Modifier.size(28.dp)) }
                            Image(painter = painterResource(id = userAvatar), contentDescription = "Profil", modifier = Modifier.padding(end = 16.dp).size(40.dp).clip(CircleShape).background(Color(0xFFFFE0B2)).border(1.dp, Color.White, CircleShape).clickable { onProfileClick() }, contentScale = ContentScale.Crop)
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = brandBlue)
                    )
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                    NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Ana Sayfa") }, selected = selectedItem == 0, onClick = { selectedItem = 0 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                    NavigationBarItem(icon = { Icon(Icons.Default.Search, null) }, label = { Text("Ara") }, selected = selectedItem == 1, onClick = { selectedItem = 1 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                    NavigationBarItem(icon = { Icon(Icons.Default.AddCircle, null, modifier = Modifier.size(32.dp), tint = if(selectedItem==2) brandBlue else Color.Gray) }, label = { Text("Başlık Aç") }, selected = selectedItem == 2, onClick = { selectedItem = 2 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                    NavigationBarItem(icon = { Icon(Icons.Filled.Person, null) }, label = { Text("Topluluk") }, selected = selectedItem == 3, onClick = { selectedItem = 3 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                    NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Favoriler") }, selected = selectedItem == 4, onClick = { selectedItem = 4 }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE3F2FD)))
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                when (selectedItem) {
                    0 -> HomeContent(userName, viewModel, onClubClick)
                    // ARTIK NULL YOK -> navController'ı parametre olarak gönderiyoruz
                    1 -> SearchScreen(navController = navController, currentUserName = userName, currentUserAvatar = userAvatar)
                    2 -> CreatePostScreen(navController = navController, userName = userName, userAvatar = userAvatar)
                    3 -> CommunityScreen()
                    4 -> FavoritesScreen(currentUserName = userName, currentUserAvatar = userAvatar, onBackClick = { selectedItem = 0 })
                }
            }
        }
    }
}

@Composable
fun HomeContent(userName: String, viewModel: ClubsViewModel, onClubClick: (Int) -> Unit) {
    val clubs by viewModel.clubs.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        item {
            Text(text = "Merhaba $userName!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Önerilen Topluluklar", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(clubs) { club -> ClubCardHorizontal(club.name, club.imageUrl) { onClubClick(club.id) } }
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
        val gundemListesi = listOf("mutlu olmayı becerenler" to "42", "final haftası" to "11", "seçmeli ders seçimi" to "21", "ring saat değişikliği" to "29")
        items(gundemListesi) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(it.first, fontSize = 16.sp, color = Color.Gray); Text(it.second, fontSize = 16.sp, color = Color.Gray)
            }
            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
        }
    }
}

@Composable
fun ClubCardHorizontal(name: String, imageUrl: String?, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.size(width = 140.dp, height = 160.dp)) {
        Column {
            Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color.White), contentAlignment = Alignment.Center) { if (imageUrl != null) AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.size(80.dp)) }
            Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF1976D2)).padding(8.dp), contentAlignment = Alignment.Center) { Text(name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1) }
        }
    }
}