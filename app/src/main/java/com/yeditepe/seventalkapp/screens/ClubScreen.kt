package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yeditepe.seventalkapp.viewmodel.ClubsViewModel

@Composable
fun ClubsScreen() {

    val viewModel: ClubsViewModel = viewModel()

    val clubsList by viewModel.clubs.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Kulüpler",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF152D53), // Koyu Mavi
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(clubsList) { club ->
                ClubItem(name = club.name, desc = club.description)
            }
        }
    }
}

@Composable
fun ClubItem(name: String, desc: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = desc, fontSize = 14.sp, color = Color.Gray)
        }
    }
}