package com.yeditepe.seventalkapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit // Çıkış yapınca veya hesap silince çağrılacak
) {
    val context = LocalContext.current
    val brandBlue = Color(0xFF1976D2)
    val lightGray = Color(0xFFF5F5F5) // Görseldeki buton arka plan rengi

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri", tint = Color.White)
                    }
                },
                title = {
                    Text(
                        text = "Ayarlar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
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
                .padding(24.dp)
        ) {
            // BAŞLIK
            Text(
                text = "Hesap Ayarları",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 1. HESAP SİLME BUTONU
            SettingsButton(
                text = "Hesap Silme",
                backgroundColor = lightGray,
                textColor = Color.DarkGray,
                onClick = {
                    // Toast mesajı göster
                    Toast.makeText(context, "Hesap başarıyla silindi", Toast.LENGTH_SHORT).show()
                    // Login ekranına at
                    onLogout()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 2. ÇIKIŞ YAP BUTONU
            SettingsButton(
                text = "Çıkış Yap",
                backgroundColor = lightGray, // İstersen hafif kırmızımsı yapabilirsin: Color(0xFFFFEBEE)
                textColor = Color.Red,
                icon = Icons.Default.ExitToApp,
                onClick = {
                    // Direkt Login ekranına at
                    onLogout()
                }
            )
        }
    }
}

// Görseldeki buton tasarımına uygun bileşen
@Composable
fun SettingsButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, tint = textColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}