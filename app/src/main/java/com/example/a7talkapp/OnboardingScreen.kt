package com.example.a7talkapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OnboardingScreen(navController: NavController) {
    // PDF'teki ilgi alanları listesi [cite: 44-55]
    val interests = listOf(
        "Not", "Kahve", "Spor", "Satranç", "Eğlence",
        "Yemek", "İngilizce", "Moda", "Müzik",
        "Kitap", "Oyun", "Sinema", "Teknoloji"
    )

    // Seçilenleri tutan liste
    val selectedInterests = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Başlık ---
        Text(
            text = "Hoş Geldin! 👋",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2244CC)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sana uygun içerikleri görmek için en az 3 ilgi alanı seç.",
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- İlgi Alanı Baloncukları (Grid Yapısı) ---
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp), // Ekrana sığacak şekilde ayarlar
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f) // Ekranın ortasını kaplasın
        ) {
            items(interests) { interest ->
                val isSelected = selectedInterests.contains(interest)

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (isSelected) {
                            selectedInterests.remove(interest)
                        } else {
                            selectedInterests.add(interest)
                        }
                    },
                    label = {
                        Text(
                            text = interest,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFE0E6FF), // Seçiliyken açık mavi
                        selectedLabelColor = Color(0xFF2244CC)      // Yazısı koyu mavi
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Devam Et Butonu ---
        Button(
            onClick = {
                // Seçim yapıldıysa Ana Sayfaya yönlendir
                navController.navigate(Screen.Home.route) {
                    // Geri tuşuna basınca tekrar bu ekrana dönmesin diye geçmişi siliyoruz
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            },
            enabled = selectedInterests.size >= 3, // 3 tane seçmeden buton açılmaz
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2244CC))
        ) {
            Text("Keşfetmeye Başla >")
        }
    }
}