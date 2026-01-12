// Dosyan 'model' klasöründe olduğu için paket ismi böyle olmalı:
package com.yeditepe.seventalkapp.data.model

import androidx.compose.runtime.mutableStateListOf

// Gönderi Veri Modeli
data class PostData(val title: String, val content: String)

// ORTAK POST KUTUSU (PostManager)
object PostManager {
    // Başlangıç örnek verileri
    val posts = mutableStateListOf(
        PostData("7Talk Valorant odası", "gerçekten rekabetçi işine gönül vermiş..."),
        PostData("Yeni bir oyun tavsiyesi: Hollow Horizon", "Son zamanlarda oynadığım...")
    )

    fun addPost(post: PostData) {
        // Yeni gönderiyi listenin EN BAŞINA ekle
        posts.add(0, post)
    }
}