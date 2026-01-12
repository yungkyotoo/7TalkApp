package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeditepe.seventalkapp.R

@Composable
fun SidebarContent(
    onMenuItemClick: (String) -> Unit
) {
    val brandBlue = Color(0xFF1976D2)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp) // Menü genişliği
            .background(brandBlue)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // 1. AYARLAR (Sorun muhtemelen buradaydı)
        SidebarItem(
            icon = Icons.Default.Settings,
            text = "Ayarlar",
            // ÖNEMLİ: Burası "Ayarlar" stringini göndermeli!
            onClick = { onMenuItemClick("Ayarlar") }
        )

        Divider(color = Color.White.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 16.dp))

        // 2. TOPLULUKLARIN BAŞLIĞI
        Text("Toplulukların", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(bottom = 12.dp))

        // Kulüpler (Tıklanınca ID gönderiyor)
        SidebarCommunityItem(text = "Basketbol", iconRes = R.drawable.ppgroup1, onClick = { onMenuItemClick("Club_1") })
        SidebarCommunityItem(text = "Dans", iconRes = R.drawable.ppgroup2, onClick = { onMenuItemClick("Club_2") })
        SidebarCommunityItem(text = "Dağcılık", iconRes = R.drawable.ppgroup3, onClick = { onMenuItemClick("Club_3") })

        Divider(color = Color.White.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 16.dp))

        // 3. 7TALK BÖLÜMÜ
        Text("7Talk:", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(bottom = 12.dp))

        SidebarItem(icon = Icons.Default.Info, text = "Sözleşmeler", onClick = { onMenuItemClick("Contracts") })
        SidebarItem(icon = Icons.Default.List, text = "Kurallar", onClick = { /* Kurallar sayfası varsa buraya eklenir */ })
        SidebarItem(icon = Icons.Default.Call, text = "İletişim", onClick = { onMenuItemClick("Contact") })
        SidebarItem(icon = Icons.Default.Face, text = "Sıkça Sorulan Sorular", onClick = { onMenuItemClick("SSS") })
    }
}

// --- YARDIMCI BİLEŞENLER ---

@Composable
fun SidebarItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun SidebarCommunityItem(text: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Tıklama özelliği eklendi
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.White, fontSize = 16.sp)
    }
}