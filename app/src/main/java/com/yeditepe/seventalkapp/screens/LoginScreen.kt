package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeditepe.seventalkapp.R // Kendi paket ismin olduğundan emin ol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit // <-- Yeni eklediğimiz özellik
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- LOGO KISMI ---
        // Uygulama çökmemesi için foreground (ön plan) kullanıyoruz
        Image(
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = "Uygulama Logosu",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Hoş Geldiniz",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2) // Mavi ton
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email Alanı
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta Adresi") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Şifre Alanı
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Giriş Butonu
        Button(
            onClick = {
                // Buraya ileride Firebase kontrolü gelecek
                // Şimdilik direkt ana sayfaya yönlendiriyoruz:
                onLoginClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
        ) {
            Text(text = "Giriş Yap", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kayıt Ol Linki
        Row {
            Text(text = "Hesabın yok mu? ")
            Text(
                text = "Kayıt Ol",
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
    }
}