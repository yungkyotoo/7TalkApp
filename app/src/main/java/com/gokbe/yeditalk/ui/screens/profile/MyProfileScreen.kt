package com.gokbe.yeditalk.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gokbe.yeditalk.ui.theme.BlueYeditepe
import com.gokbe.yeditalk.ui.theme.GreenYeditepe
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import com.gokbe.yeditalk.ui.components.AvatarSelectionDialog
import com.gokbe.yeditalk.ui.components.AvatarAssets
import androidx.compose.material.icons.filled.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    var showAvatarDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profilim") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Ayarlar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueYeditepe,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Profile Image Placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEFB8C8)) // Matching TopBar pink
                    .clickable { showAvatarDialog = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AvatarAssets.avatars.getOrElse(user.avatarId) { Icons.Default.Person },
                    contentDescription = "Profile Picture",
                    tint = Color.Black,
                    modifier = Modifier.size(72.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = user.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(text = user.department, color = Color.Gray)
            
            if (user.bio.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = user.bio, color = Color.DarkGray, fontSize = 14.sp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onNavigateToEditProfile,
                colors = ButtonDefaults.buttonColors(containerColor = GreenYeditepe)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text("Profili Düzenle")
            }
            
            // User Content Listings would go here (LazyColumn)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Paylaşımlarım", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
            // ... Content list ...
        }
    }

    if (showAvatarDialog) {
        AvatarSelectionDialog(
            currentAvatarId = user.avatarId,
            onAvatarSelected = { newId ->
                viewModel.updateAvatar(newId)
                showAvatarDialog = false
            },
            onDismissRequest = { showAvatarDialog = false }
        )
    }
}
