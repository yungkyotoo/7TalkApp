package com.example.a7talkapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Üst Başlık ve Geri Butonu (PDF Sayfa 17 [cite: 291]) ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri")
            }
            Text(
                text = "Hesap ayarları",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2244CC), // Tema Rengi
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // --- Menü Listesi (PDF Sayfa 17 [cite: 289-290]) ---

        // 1. Bildirimler
        SettingsItem(title = "Bildirimler") {
            // Bildirim ayarlarına gitme kodu buraya gelecek
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Çıkış Yap
        SettingsItem(title = "Çıkış yap") {
            // Çıkış yapma mantığı buraya
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Hesabı Sil
        SettingsItem(title = "Hesabı sil", isDestructive = true) {
            // Hesap silme onayı buraya
        }
    }
}

// Tekrar eden menü elemanları için özel bileşen (Custom Composable)
@Composable
fun SettingsItem(
    title: String,
    isDestructive: Boolean = false, // Kırmızı yazı olsun mu? (Hesap silme gibi)
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp)) // Kenarları yuvarlatılmış
            .background(Color(0xFFE0E6FF)) // PDF'teki hafif mavi zemin rengi
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (isDestructive) Color.Red else Color.Black // "Hesabı Sil" ise kırmızı yap
        )

        // Sağdaki ok işareti
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color(0xFF2244CC)
        )
    }
}