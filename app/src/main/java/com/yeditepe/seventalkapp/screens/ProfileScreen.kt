package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch // Scope için gerekli
import com.yeditepe.seventalkapp.R
import com.yeditepe.seventalkapp.data.model.PostData
import com.yeditepe.seventalkapp.data.model.PostManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userName: String,
    userAvatar: Int,
    onBackClick: () -> Unit,
    onSidebarItemClick: (String) -> Unit
) {
    // --- STATE YÖNETİMİ ---
    var currentName by remember { mutableStateOf(userName) }
    var currentBio by remember { mutableStateOf("Hello world!") }
    var currentAvatar by remember { mutableStateOf(userAvatar) }

    val interestList = listOf("Not", "Kahve", "Spor", "Satranç", "Eğlence")
    var showEditDialog by remember { mutableStateOf(false) }
    val brandBlue = Color(0xFF1976D2)

    // --- DRAWER (YAN MENÜ) TANIMLAMALARI ---
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (showEditDialog) {
        EditProfileDialog(
            currentName = currentName,
            currentBio = currentBio,
            currentAvatar = currentAvatar,
            currentInterests = interestList,
            onDismiss = { showEditDialog = false },
            onSave = { newName, newBio, newAvatar ->
                currentName = newName
                currentBio = newBio
                currentAvatar = newAvatar
                showEditDialog = false
            }
        )
    }

    // TÜM SAYFAYI DRAWER İÇİNE ALIYORUZ
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = brandBlue,
                drawerContentColor = Color.White,
                modifier = Modifier.width(300.dp)
            ) {
                SidebarContent(
                    onMenuItemClick = { item ->
                        scope.launch { drawerState.close() }
                        onSidebarItemClick(item)
                    }
                )
            }
        }
    ) {
        //  SCAFFOLD
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {

                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, "Menü", tint = Color.White, modifier = Modifier.size(32.dp))
                        }
                    },
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.img_7alkhomescreen),
                            contentDescription = "Logo",
                            modifier = Modifier.height(250.dp),
                            contentScale = ContentScale.Fit
                        )
                    },
                    actions = {
                        BadgedBox(
                            badge = { Badge(containerColor = Color.Red, contentColor = Color.White) { Text("3") } },
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Icon(Icons.Default.Notifications, "Bildirimler", tint = Color.White, modifier = Modifier.size(28.dp))
                        }
                        Image(
                            painter = painterResource(id = currentAvatar),
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = brandBlue)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                    NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Ana Sayfa") }, selected = false, onClick = { onBackClick() })
                    NavigationBarItem(icon = { Icon(Icons.Default.Search, null) }, label = { Text("Ara") }, selected = false, onClick = {})
                    NavigationBarItem(icon = { Icon(Icons.Default.AddCircle, null) }, label = { Text("Başlık Aç") }, selected = false, onClick = {})
                    NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Topluluk") }, selected = true, onClick = {})
                    NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Favoriler") }, selected = false, onClick = {})
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                item {
                    ProfileHeaderSection(
                        userName = currentName,
                        userBio = currentBio,
                        userAvatar = currentAvatar,
                        onEditClick = { showEditDialog = true }
                    )
                }
                item { FilterChipsRow(interestList) }
                item {
                    Text(
                        text = "Entryler & Başlıklar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray)
                }
                val posts = PostManager.posts
                items(posts) { post ->
                    PostItem(post, currentName, currentAvatar)
                }
            }
        }
    }
}

@Composable
fun FilterChipsRow(interestList: List<String>) {
    val displayList = if (interestList.isEmpty()) listOf("İlgi alanı eklenmedi") else interestList
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(displayList) { chip ->
            SuggestionChip(
                onClick = {},
                label = { Text(chip) },
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFE3F2FD), labelColor = Color(0xFF1976D2)),
                border = BorderStroke(1.dp, Color(0xFF1976D2)),
                shape = RoundedCornerShape(50)
            )
        }
    }
}

@Composable
fun EditProfileDialog(currentName: String, currentBio: String, currentAvatar: Int, currentInterests: List<String>, onDismiss: () -> Unit, onSave: (String, String, Int) -> Unit) {
    // ... (Senin mevcut kodunla aynı) ...
    // Burayı uzunluk olmasın diye kısalttım, senin dosyadaki haliyle kalsın.
    // Eğer burası lazımsa söyleyebilirsin ama değişmedi.
    var tempName by remember { mutableStateOf(currentName) }
    var tempBio by remember { mutableStateOf(currentBio) }
    var tempAvatar by remember { mutableStateOf(currentAvatar) }

    val avatarList = listOf(
        R.drawable.ppgroup1, R.drawable.ppgroup2, R.drawable.ppgroup3,
        R.drawable.ppgroup4, R.drawable.ppgroup5, R.drawable.ppgroup6,
        R.drawable.ppgroup7, R.drawable.ppgroup8
    )

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Card(modifier = Modifier.fillMaxWidth(0.9f).padding(16.dp), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ArrowBack, "Geri", modifier = Modifier.clickable { onDismiss() })
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Düzenle", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.height(120.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(avatarList) { avatarId ->
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(60.dp).clip(CircleShape).border(if (tempAvatar == avatarId) 3.dp else 0.dp, if (tempAvatar == avatarId) Color(0xFF1976D2) else Color.Transparent, CircleShape).clickable { tempAvatar = avatarId }) {
                            Image(painter = painterResource(id = avatarId), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = tempName, onValueChange = { tempName = it }, label = { Text("Kullanıcı Adı") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = tempBio, onValueChange = { tempBio = it }, label = { Text("Bio") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(16.dp))
                FilterChipsRow(currentInterests)
                Spacer(modifier = Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { onSave(tempName, tempBio, tempAvatar) }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))) { Text("Kaydet") }
                    Button(onClick = { onDismiss() }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))) { Text("İptal", color = Color.Black) }
                }
            }
        }
    }
}

@Composable
fun ProfileHeaderSection(userName: String, userBio: String, userAvatar: Int, onEditClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE3F2FD)).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(20.dp)).background(Color(0xFFEDB9B9)), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = userAvatar), contentDescription = null, modifier = Modifier.size(80.dp).clip(CircleShape), contentScale = ContentScale.Crop)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(userName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Bilgisayar Mühendisliği |", fontSize = 12.sp, color = Color.Gray)
                Text(userBio, fontSize = 14.sp, color = Color.DarkGray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onEditClick, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(12.dp)) { Text("Düzenle") }
            Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(12.dp)) { Text("Paylaş") }
        }
    }
}

@Composable
fun PostItem(post: PostData, userName: String, userAvatar: Int) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = userAvatar), contentDescription = null, modifier = Modifier.size(32.dp).clip(CircleShape).background(Color(0xFFEDB9B9)), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(8.dp))
                Text(userName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(post.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(post.content, fontSize = 13.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(12.dp))
            Row { Icon(Icons.Default.ThumbUp, null, tint = Color.Gray); Spacer(modifier = Modifier.width(16.dp)); Icon(Icons.Default.Email, null, tint = Color.Gray) }
        }
    }
}