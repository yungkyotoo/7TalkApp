package com.example.a7talkapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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
fun ProfileScreen(navController: NavController, userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Avatar ve Takipçi Sayıları (Row) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Profil Resmi (Sembolik İkon)
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E6FF))
                    .padding(10.dp),
                tint = Color(0xFF2244CC)
            )

            // Takipçi İstatistikleri [cite: 225, 234]
            StatItem(count = userProfile.followers, label = "Takipçi")
            StatItem(count = userProfile.following, label = "Takip")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- İsim ve Bölüm ---
        Text(text = userProfile.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = userProfile.department, color = Color.Gray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // --- Kısa Bio ---
        Text(text = userProfile.bio, modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // --- İlgi Alanları (Chips) [cite: 230] ---
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(userProfile.interests) { interest ->
                SuggestionChip(
                    onClick = { },
                    label = { Text(interest) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Düzenle Butonu ---
        Button(
            onClick = { navController.navigate(Screen.EditProfile.route) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2244CC))
        ) {
            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Profili Düzenle")
        }
    }
}

@Composable
fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}
