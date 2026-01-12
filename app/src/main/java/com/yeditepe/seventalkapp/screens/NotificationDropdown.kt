package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup

@Composable
fun NotificationDropdown(
    onDismiss: () -> Unit
) {
    // Veriler
    val notifications = listOf(
        NotificationItemData(
            title = "t/Dans Kulübü",
            content = "Yeni sezon başlıyor! Ritim ve tutku dolu derslerimize katılmak ve sahne heyecanını yaşamak için seni aramızda görmek istiyoruz."
        ),
        NotificationItemData(
            title = "t/Basketbol",
            content = "Antrenmanlara hazır mıyız? Takım ruhu, disiplin ve sportmenlik ile dolu bir sezon için çalışmalarımız hız kesmeden devam ediyor."
        ),
        NotificationItemData(
            title = "7TALK",
            content = "İlgilenebileceğini düşündüğümüz \"ring saatlerinde değişiklik\" popüler oldu."
        )
    )

    // Popup Bileşeni
    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(x = -50, y = 140),
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            notifications.forEach { notification ->
                NotificationCard(notification)
            }
        }
    }
}

@Composable
fun NotificationCard(data: NotificationItemData) {
    val brandBlue = Color(0xFF1976D2)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Mavi Başlık
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brandBlue)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = data.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            // Beyaz İçerik
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = data.content,
                    color = Color.Black,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

// Veri Modeli
data class NotificationItemData(
    val title: String,
    val content: String
)
