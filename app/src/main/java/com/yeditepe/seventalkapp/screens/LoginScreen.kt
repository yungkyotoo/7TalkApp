package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeditepe.seventalkapp.components.TalkButton
import com.yeditepe.seventalkapp.components.TalkInput


// Fake colors at the moment

@Composable
fun LoginScreen(onRegisterClick: () -> Unit) {
    // States
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Fake colors
    val blueBackground = Color(0xFFCBE8F9)
    val darkBlueLogo = Color(0xFF152D53)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blueBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            // LOGO AREA
            Text(
                text = "7ALK", // Buraya ileride resim (Image) gelecek
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = darkBlueLogo,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // WHITE CARD AREA
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Email Input
                    TalkInput(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "y***@std.yeditepe.edu.tr",
                        icon = Icons.Default.Email
                    )

                    // Password Input
                    TalkInput(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "********",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login Button
                    TalkButton(text = "Giriş") {

                        println("Giriş butonuna basıldı: $email")
                    }

                    // Alt Links
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Şifremi Unuttum",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable { /* Navigasyon */ }
                        )

                        Text(
                            text = "Kayıt Ol",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = darkBlueLogo,
                            modifier = Modifier.clickable { onRegisterClick() }
                        )
                    }
                }
            }
        }
    }
}