package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeditepe.seventalkapp.R

@Composable
fun CommunityScreen() {
    val brandBlue = Color(0xFF1E75D8)

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5)).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Merhaba Ahmet Töz!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        item {
            Text("Önerilen Topluluklar", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
        }
        item { CommunityListRow(brandBlue) }
        items(sampleCommunityPosts) { post -> CommunityPostCard(post, brandBlue) }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun CommunityListRow(brandColor: Color) {
    val communities = listOf(
        Triple("Dans Kulübü", R.drawable.dans, brandColor),
        Triple("Basketbol", R.drawable.basketball, brandColor),
        Triple("Doğa Sporları", R.drawable.dagcilik, brandColor)
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        items(communities) { item ->
            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier.width(140.dp).height(100.dp)) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(painter = painterResource(id = item.second), contentDescription = item.first, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                    Box(modifier = Modifier.fillMaxWidth().height(30.dp).align(Alignment.BottomCenter).background(item.third.copy(alpha = 0.9f)), contentAlignment = Alignment.Center) {
                        Text(item.first, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CommunityPostCard(post: CommunityPostData, brandColor: Color) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(post.avatarColor), contentAlignment = Alignment.Center) { Icon(Icons.Filled.Person, contentDescription = null) }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(post.username, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(post.department, color = Color.Gray, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(post.content, fontSize = 14.sp, lineHeight = 20.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.like), "Like", modifier = Modifier.size(20.dp), tint = Color.Unspecified); Text(" ${post.likes}", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(painter = painterResource(id = R.drawable.dislike), "Dislike", modifier = Modifier.size(20.dp), tint = Color.Unspecified)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(painter = painterResource(id = R.drawable.comment), "Comment", modifier = Modifier.size(20.dp), tint = Color.Unspecified); Text(" ${post.comments}", fontSize = 12.sp, color = Color.Gray)
                }
                Icon(Icons.Filled.MoreVert, null, tint = Color.Gray)
            }
        }
    }
}

data class CommunityPostData(val username: String, val department: String, val communityTag: String, val content: String, val likes: Int, val comments: Int, val avatarColor: Color)
val sampleCommunityPosts = listOf(CommunityPostData("ahmettoz", "Bilgisayar Müh.", "Dans", "Festival ne zaman?", 0, 0, Color(0xFFF8BBD0)), CommunityPostData("nildemir", "RTS", "Basketbol", "Maça gelen?", 0, 2, Color(0xFFB3E5FC)))