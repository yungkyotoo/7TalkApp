package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubDetailScreen(
    clubId: Int, // ID BURAYA GELİYOR (1=Basketbol, 2=Dans, 3=Dağcılık)
    onBackClick: () -> Unit
) {
    val brandBlue = Color(0xFF1976D2)
    val brandGreen = Color(0xFF4CAF50)
    val lightGrayBg = Color(0xFFF5F5F5)

    val clubData = remember(clubId) { getClubDataById(clubId) }

    var showAnnouncements by remember { mutableStateOf(false) }

    if (showAnnouncements) {
        AnnouncementsDialog(
            clubName = clubData.name,
            clubImage = clubData.imageRes,
            announcements = clubData.announcements,
            onDismiss = { showAnnouncements = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Geri", tint = Color.White)
                    }
                },
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.img_7alkhomescreen),
                        contentDescription = "7Talk Logo",
                        modifier = Modifier.height(250.dp),
                        contentScale = ContentScale.Fit
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(id = R.drawable.notificationbell), contentDescription = "Bildirim", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ppgroup1),
                        contentDescription = "Profil",
                        modifier = Modifier.padding(end = 16.dp).size(36.dp).clip(CircleShape).background(Color(0xFFFFE0B2)),
                        contentScale = ContentScale.Crop
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brandBlue)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGrayBg)
                .padding(paddingValues)
        ) {
            // --- 1. KULÜP BİLGİ KARTI ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Kulüp Resmi ve İsmi
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier.size(width = 120.dp, height = 120.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = clubData.imageRes),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .background(brandBlue)
                                    .padding(vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(clubData.name, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // İstatistikler
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            InfoChip(text = "${clubData.memberCount} Üye", color = brandBlue)
                            InfoChip(text = "${clubData.entryCount} Entry", color = brandBlue)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            InfoChip(text = "Duyurular", color = brandGreen, onClick = { showAnnouncements = true })
                            InfoChip(text = "Topluluk", color = brandGreen)
                        }
                    }
                }
            }

            // --- 2. GÖNDERİ LİSTESİ ---
            items(clubData.posts) { post ->
                ClubPostItem(post)
            }
        }
    }
}

// --- DATA VE MANTIK KISMI ---

// Kulüp Verilerini Tutan Model
data class ClubFullData(
    val name: String,
    val imageRes: Int,
    val memberCount: String,
    val entryCount: String,
    val posts: List<ClubPost>,
    val announcements: List<AnnouncementData>
)

// ID'ye göre veri getiren fonksiyon
fun getClubDataById(id: Int): ClubFullData {
    return when (id) {
        // --- ID 2: DANS KULÜBÜ ---
        2 -> ClubFullData(
            name = "Dans",
            imageRes = R.drawable.dans,
            memberCount = "850",
            entryCount = "1.5B",
            posts = listOf(
                ClubPost("melis_y", "Dans Eğitmeni", "Salsa kurslarımız haftaya başlıyor, kayıt olmayı unutmayın! 💃", R.drawable.ppgroup4, 15, 3),
                ClubPost("berk_dance", "Üye", "Partner arıyorum, bachata bilen var mı?", R.drawable.ppgroup5, 5, 8)
            ),
            announcements = listOf(
                AnnouncementData("melis_y", "Başkan", R.drawable.ppgroup4, "Cuma akşamı Taksim'de dans gecesi düzenliyoruz. Servis kalkış saati 20:00.", "12.01.2026"),
                AnnouncementData("Admin", "Yönetim", R.drawable.ppgroup2, "Dönem sonu gösterisi provaları A blok aynalı salonda yapılacak.", "10.01.2026")
            )
        )

        // --- ID 3: DAĞCILIK KULÜBÜ ---
        3 -> ClubFullData(
            name = "Dağcılık",
            imageRes = R.drawable.dagcilik,
            memberCount = "420",
            entryCount = "950",
            posts = listOf(
                ClubPost("can_zirve", "Mimarlık", "Hafta sonu Uludağ yürüyüşü için 2 kişilik yerimiz açıldı, gelmek isteyen?", R.drawable.ppgroup6, 25, 12),
                ClubPost("elif_kmp", "Psikoloji", "Kış kampı için uygun fiyatlı uyku tulumu önerisi olan var mı?", R.drawable.ppgroup7, 10, 5)
            ),
            announcements = listOf(
                AnnouncementData("Yönetim", "Kulüp Başkanı", R.drawable.ppgroup3, "Likya Yolu yürüyüşü kayıtları başlamıştır! Son başvuru tarihi 20 Ocak.", "12.01.2026"),
                AnnouncementData("Eğitim Birimi", "Eğitmen", R.drawable.ppgroup3, "Temel düğüm atma ve kampçılık eğitimi bu Çarşamba B204'te.", "09.01.2026")
            )
        )

        // --- ID 1 ---
        else -> ClubFullData(
            name = "Basketbol",
            imageRes = R.drawable.basketball,
            memberCount = "1.2B",
            entryCount = "2.3B",
            posts = listOf(
                ClubPost("ahmettoz", "Bilgisayar Mühendisliği", "Pazartesi günkü antrenmanda gözlüğümü kaybettim gören oldu mu?", R.drawable.ppgroup1, 0, 0),
                ClubPost("nildemir", "Radyo TV", "Yarınki maça kimler geliyor?", R.drawable.ppgroup2, 0, 2)
            ),
            announcements = listOf(
                AnnouncementData("ahmettoz", "Topluluk Admini", R.drawable.ppgroup1, "Erkek takımı maçı Cuma 16.00'da. Tribün dolsun!", "10.01.2026"),
                AnnouncementData("ahmettoz", "Topluluk Admini", R.drawable.ppgroup1, "Dönem sonu partisi 9 Ocak'ta. Formu doldurun!", "03.01.2026")
            )
        )
    }
}

// --- BİLEŞENLER ---

@Composable
fun AnnouncementsDialog(clubName: String, clubImage: Int, announcements: List<AnnouncementData>, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight(0.8f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = clubImage), contentDescription = null, modifier = Modifier.size(50.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("$clubName Topluluğu", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Duyuru Panosu", color = Color.Gray, fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(announcements) { ann ->
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = ann.avatarRes), contentDescription = null, modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEDB9B9)), contentScale = ContentScale.Crop)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column { Text(ann.name, fontWeight = FontWeight.Bold, fontSize = 15.sp); Text(ann.role, fontSize = 12.sp, color = Color.Gray) }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(ann.content, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(ann.date, fontSize = 11.sp, color = Color.Gray, modifier = Modifier.align(Alignment.End))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(text: String, color: Color, onClick: (() -> Unit)? = null) {
    Surface(
        color = color,
        shape = RoundedCornerShape(50),
        modifier = Modifier.height(24.dp).clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(text = text, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ClubPostItem(post: ClubPost) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = post.avatarRes), contentDescription = null, modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFE0E0E0)), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(12.dp))
                Column { Text(post.username, fontWeight = FontWeight.Bold, fontSize = 16.sp); Text(post.department, fontSize = 12.sp, color = Color.Gray) }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(post.content, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.like), contentDescription = "Like", modifier = Modifier.size(20.dp), tint = Color.Gray)
                Text(" ${post.likeCount}", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp, end = 16.dp))
                Icon(painter = painterResource(id = R.drawable.dislike), contentDescription = "Dislike", modifier = Modifier.size(20.dp), tint = Color.Gray)
                Text(" 0", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp, end = 16.dp))
                Icon(painter = painterResource(id = R.drawable.comment), contentDescription = "Comment", modifier = Modifier.size(20.dp), tint = Color.Gray)
                Text(" ${post.commentCount}", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp))
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.MoreVert, "Daha Fazla", tint = Color.Gray)
            }
        }
    }
}


data class ClubPost(val username: String, val department: String, val content: String, val avatarRes: Int, val likeCount: Int, val commentCount: Int)
data class AnnouncementData(val name: String, val role: String, val avatarRes: Int, val content: String, val date: String)