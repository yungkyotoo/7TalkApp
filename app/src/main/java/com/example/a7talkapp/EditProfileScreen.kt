package com.example.a7talkapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    currentProfile: UserProfile,
    onSave: (String, String) -> Unit // Kaydetme fonksiyonu
) {
    // Kullanıcının yazdığı yeni değerleri tutan değişkenler
    var tempName by remember { mutableStateOf(currentProfile.name) }
    var tempBio by remember { mutableStateOf(currentProfile.bio) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Profili Düzenle", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // İsim Değiştirme Alanı [cite: 593]
        OutlinedTextField(
            value = tempName,
            onValueChange = { tempName = it },
            label = { Text("Kullanıcı Adı") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bio Değiştirme Alanı [cite: 595]
        OutlinedTextField(
            value = tempBio,
            onValueChange = { tempBio = it },
            label = { Text("Kısa Bio") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // İptal Butonu
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            ) {
                Text("İptal")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Kaydet Butonu [cite: 600]
            Button(
                onClick = {
                    onSave(tempName, tempBio) // Yeni verileri ana ekrana gönder
                    navController.popBackStack() // Geri dön
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2244CC))
            ) {
                Text("Kaydet")
            }
        }
    }
}
