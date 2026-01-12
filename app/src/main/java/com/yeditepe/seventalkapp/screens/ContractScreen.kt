package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun ContractsScreen(
    onBackClick: () -> Unit
) {
    val brandBlue = Color(0xFF1976D2)

    // Sözleşme Maddeleri
    val terms = listOf(
        "Uygulama içerisinde paylaştığınız tüm içeriklerden kendiniz sorumlusunuz.",
        "Yasalara aykırı, hakaret içeren, spam veya zararlı içerikler paylaşmak yasaktır.",
        "Uygulama, kullanıcı deneyimini iyileştirmek için gerekli verileri toplayabilir ve işleyebilir.",
        "7 Talk, teknik nedenlerle hizmeti geçici olarak durdurma veya güncelleme hakkını saklı tutar.",
        "Hesabınızın güvenliği size aittir; üçüncü kişilerle paylaşmayın.",
        "Kuralların ihlali durumunda hesabınız kısıtlanabilir veya kapatılabilir."
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
                            text = "Sözleşmeler",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 48.dp) // Başlığı ortalamak için dengeleme
                        )
                    }
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
                .padding(24.dp) // Kenarlardan biraz daha geniş boşluk
                .verticalScroll(rememberScrollState()) // Uzun metinler için kaydırma
        ) {
            // Başlık
            Text(
                text = "7 Talk'u kullanarak, aşağıdaki şartları kabul etmiş sayılırsınız:",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Black,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Maddeler (Bullet Points)
            terms.forEach { term ->
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    // HATA BURADAYDI, DÜZELTİLDİ: crossAxisAlignment -> verticalAlignment
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "•", fontSize = 18.sp, color = Color.DarkGray, modifier = Modifier.padding(end = 8.dp))
                    Text(
                        text = term,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Alt Not
            Text(
                text = "Uygulamayı kullanmaya devam ederek bu koşulları kabul etmiş olursunuz.",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}