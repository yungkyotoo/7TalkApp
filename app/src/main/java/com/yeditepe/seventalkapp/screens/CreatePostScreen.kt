package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.navigation.NavController
import com.yeditepe.seventalkapp.R
// ⬇️ BU İKİ IMPORT EKLENDİ (Hatanın asıl çözümü burası)
import com.yeditepe.seventalkapp.data.model.PostData
import com.yeditepe.seventalkapp.data.model.PostManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    navController: NavController,
    userName: String,
    userAvatar: Int
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val maxCharCount = 2000

    val brandBlue = Color(0xFF1976D2)
    val brandGreen = Color(0xFF4CAF50)

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri", tint = Color.White)
                    }
                },
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Yeni Başlık",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 48.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brandBlue)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. KULLANICI KARTI
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = userAvatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEDB9B9)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = userName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "Bilgisayar Mühendisliği", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. BAŞLIK GİRİŞİ
            OutlinedTextField(
                value = title,
                // 'it' yerine 'newTitle' diyerek hatayı garanti çözdük
                onValueChange = { newTitle -> title = newTitle },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Başlık Gir", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Gray) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. İÇERİK GİRİŞİ
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = content,
                    // 'it' yerine 'newText' diyerek karmaşayı engelledik
                    onValueChange = { newText ->
                        if (newText.length <= maxCharCount) content = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    placeholder = { Text("Bir şeyler yaz.", color = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    // Hizalamayı düzelttik
                    textStyle = LocalTextStyle.current.copy(textAlign = androidx.compose.ui.text.style.TextAlign.Start)
                )

                Text(
                    text = "${content.length}/$maxCharCount",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. YAYINLA BUTONU
            Button(
                onClick = {
                    if (title.isNotEmpty() && content.isNotEmpty()) {
                        // Artık import edildiği için burası kızarmayacak
                        PostManager.addPost(PostData(title, content))

                        navController.navigate("profile/$userName/$userAvatar") {
                            popUpTo("home/$userName/$userAvatar")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = brandGreen),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp)
            ) {
                Text("Yayınla", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Yayınladığında topluluk kurallarını\nkabul etmiş olursun.",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}