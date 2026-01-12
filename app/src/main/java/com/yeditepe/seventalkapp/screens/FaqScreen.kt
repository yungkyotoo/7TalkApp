package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(
    onBackClick: () -> Unit
) {
    val brandBlue = Color(0xFF1976D2)

    // Görseldeki soruları buraya listeledim
    val faqList = listOf(
        "S1: 7Talk'a nasıl kayıt olabilirim?" to "C1: Sağ üstteki kayıt ol butonuna tıklayarak...",
        "S2: 7Talk'a kayıt oldum ancak onay mailim gelmiyor, ne yapabilirim?" to "C2: Spam klasörünüzü kontrol ediniz...",
        "S3: 7Talk'a kayıt oldum, entry de yazıyorum ama entry'lerim görünmüyor. ne yapabilirim?" to "C3: Onay sürecini beklemeniz gerekmektedir.",
        "S4: Nick'imi değiştirebilir miyim?" to "C4: Ayarlar sekmesinden bir kereye mahsus...",
        "S5: Hesabıma giriş yapmaya çalıştığımda, \"bu hesaba erişim kapatılmış\" uyarısı alıyorum?" to "C5: Topluluk kurallarını ihlal ettiğiniz için...",
        "S6: Bir başlığa yazmak istiyorum ama beni başka bir başlığa yönlendiriyor?" to "C6: Başlık birleştirilmiş olabilir."
    )

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
                            text = "S.S.S.",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 48.dp) // Ortalamak için sağ dengeleme
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brandBlue)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // itemsIndexed kullanarak satır numarasına (index) erişiyoruz
            itemsIndexed(faqList) { index, item ->
                // Çift sayılar açık mavi, tek sayılar beyaz (Zebra deseni)
                val backgroundColor = if (index % 2 == 0) Color(0xFFE3F2FD) else Color.White

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor) // <-- Rengi burası değiştiriyor
                        .padding(16.dp)
                ) {
                    Text(text = item.first, fontWeight = FontWeight.Bold, color = Color.DarkGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = item.second, color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
    }
}