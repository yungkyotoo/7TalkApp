package com.yeditepe.seventalkapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current // Toast mesajı için gerekli
    val brandBlue = Color(0xFF1976D2)
    val brandGreen = Color(0xFF4CAF50)

    // Form State'leri
    var selectedOption by remember { mutableStateOf(0) } // 0: İçerik, 1: Diğer
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri", tint = Color.White)
                    }
                },
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "İletişim",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
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
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Kaydırma özelliği
        ) {
            // 1. UYARI METİNLERİ
            Text(
                text = "Dikkat edilecek hususlar",
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            val rules = listOf(
                "Mesajınızı göndermeden önce, sormak istediğiniz sorunun sss'te cevaplanmış olup olmadığını kontrol edin.",
                "Mesajınız yazarlara iletilmemektedir.",
                "Kişiler hakkında yazılan girişlere dair şikayetler ancak gerçek kişinin kendisi tarafından yapılabilir."
            )

            rules.forEachIndexed { index, rule ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(text = "${index + 1}. ", fontSize = 13.sp, color = Color.DarkGray)
                    Text(
                        text = rule,
                        fontSize = 13.sp,
                        color = brandBlue,
                        textDecoration = TextDecoration.Underline,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. KATEGORİ SEÇİMİ
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Kategori", fontWeight = FontWeight.SemiBold, modifier = Modifier.width(80.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { selectedOption = 0 }) {
                        RadioButton(selected = selectedOption == 0, onClick = { selectedOption = 0 }, colors = RadioButtonDefaults.colors(selectedColor = brandBlue))
                        Text("7Talk'ta yayınlanan içerik", fontSize = 14.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { selectedOption = 1 }) {
                        RadioButton(selected = selectedOption == 1, onClick = { selectedOption = 1 }, colors = RadioButtonDefaults.colors(selectedColor = brandBlue))
                        Text("7Talk'la ilgili diğer konular", fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. KONU INPUT
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Konu", fontWeight = FontWeight.SemiBold, modifier = Modifier.width(80.dp))
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("#66799268 no'lu entry", color = Color.Gray, fontSize = 12.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. AÇIKLAMA INPUT (HATALI KISIM BURADAYDI, DÜZELTİLDİ)
            // Row içinde 'verticalAlignment = Alignment.Top' kullanarak yazıyı yukarı sabitledik.
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "Açıklama",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.width(80.dp).padding(top = 12.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("(Opsiyonel)", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. GÖNDER BUTONU
            Button(
                onClick = {
                    Toast.makeText(context, "Başarıyla gönderildi", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = brandGreen),
                shape = RoundedCornerShape(50)
            ) {
                Text("Gönder", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}