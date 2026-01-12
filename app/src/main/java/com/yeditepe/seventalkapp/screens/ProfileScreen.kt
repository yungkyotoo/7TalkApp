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
import com.yeditepe.seventalkapp.R
// ⬇️ BU İKİ IMPORT ÇOK ÖNEMLİ (Veri Modelini buradan çekiyoruz)
import com.yeditepe.seventalkapp.data.model.PostData
import com.yeditepe.seventalkapp.data.model.PostManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userName: String,
    userAvatar: Int,
    onBackClick: () -> Unit
) {
    // --- STATE YÖNETİMİ ---
    var currentName by remember { mutableStateOf(userName) }
    var currentBio by remember { mutableStateOf("Hello world!") }
    var currentAvatar by remember { mutableStateOf(userAvatar) }

    // Düzenle penceresi kontrolü
    var showEditDialog by remember { mutableStateOf(false) }

    val brandBlue = Color(0xFF1976D2)

    // --- DÜZENLEME PENCERESİ (DIALOG) ---
    if (showEditDialog) {
        EditProfileDialog(
            currentName = currentName,
            currentBio = currentBio,
            currentAvatar = currentAvatar,
            onDismiss = { showEditDialog = false },
            onSave = { newName, newBio, newAvatar ->
                currentName = newName
                currentBio = newBio
                currentAvatar = newAvatar
                showEditDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /* Menü */ }) {
                        Icon(Icons.Default.Menu, "Menü", tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                },
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.img_7alkhomescreen),
                        contentDescription = "Logo",
                        modifier = Modifier.height(28.dp),
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
            item { FilterChipsRow() }
            item {
                Text(
                    text = "Entryler & Başlıklar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray)
            }

            // --- GÖNDERİ LİSTESİ (GÜNCELLENDİ) ---
            // Artık PostManager'dan gelen CANLI veriyi kullanıyoruz
            val posts = PostManager.posts

            items(posts) { post ->
                PostItem(post, currentName, currentAvatar)
            }
        }
    }
}

// --- DÜZENLEME EKRANI TASARIMI ---
@Composable
fun EditProfileDialog(
    currentName: String,
    currentBio: String,
    currentAvatar: Int,
    onDismiss: () -> Unit,
    onSave: (String, String, Int) -> Unit
) {
    var tempName by remember { mutableStateOf(currentName) }
    var tempBio by remember { mutableStateOf(currentBio) }
    var tempAvatar by remember { mutableStateOf(currentAvatar) }

    val avatarList = listOf(
        R.drawable.ppgroup1, R.drawable.ppgroup2, R.drawable.ppgroup3,
        R.drawable.ppgroup4, R.drawable.ppgroup5, R.drawable.ppgroup6,
        R.drawable.ppgroup7, R.drawable.ppgroup8
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Geri",
                        modifier = Modifier.clickable { onDismiss() }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Düzenle", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Profil Fotoğrafı", modifier = Modifier.fillMaxWidth(), color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.height(180.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(avatarList) { avatarId ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .border(
                                    width = if (tempAvatar == avatarId) 3.dp else 0.dp,
                                    color = if (tempAvatar == avatarId) Color(0xFF1976D2) else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { tempAvatar = avatarId }
                        ) {
                            Image(
                                painter = painterResource(id = avatarId),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            if (tempAvatar == avatarId) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF1976D2),
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(Color.White, CircleShape)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Kullanıcı Adı", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = tempName,
                    onValueChange = { tempName = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("Kısa Bio", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = tempBio,
                    onValueChange = { tempBio = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("İlgi Alanları", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.SemiBold)
                FilterChipsRow()

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { onSave(tempName, tempBio, tempAvatar) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Kaydet")
                    }
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("İptal", color = Color.Black)
                    }
                }
            }
        }
    }
}

// --- HEADER BİLEŞENİ ---
@Composable
fun ProfileHeaderSection(
    userName: String,
    userBio: String,
    userAvatar: Int,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFEDB9B9)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = userAvatar),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = userName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Bilgisayar Mühendisliği |", fontSize = 12.sp, color = Color.Gray)
                Text(text = "Topluluk Kurucusu", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = userBio, fontSize = 14.sp, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("1.2B Takipçi", fontWeight = FontWeight.Bold)
            Text("350 Takip", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onEditClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Düzenle")
            }
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Paylaş")
            }
        }
    }
}

@Composable
fun FilterChipsRow() {
    val chips = listOf("Not", "Kahve", "Spor", "Satranç", "Eğlence")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chips) { chip ->
            SuggestionChip(
                onClick = {},
                label = { Text(chip) },
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFE3F2FD)),
                border = BorderStroke(1.dp, Color(0xFF1976D2))
            )
        }
    }
}

@Composable
fun PostItem(post: PostData, userName: String, userAvatar: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = userAvatar),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(Color(0xFFEDB9B9)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = userName.lowercase().replace(" ", ""), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = "Bilgisayar Mühendisliği", fontSize = 10.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.content, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ThumbUp, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Text(" 0", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Text(" 0", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Text(" 0", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}