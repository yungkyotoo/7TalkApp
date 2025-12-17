package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeditepe.seventalkapp.components.TalkButton

@Composable
fun RegisterScreen(onLoginClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Burası Kayıt Ekranı", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        TalkButton(text = "Giriş'e Geri Dön") {
            onLoginClick()
        }
    }
}