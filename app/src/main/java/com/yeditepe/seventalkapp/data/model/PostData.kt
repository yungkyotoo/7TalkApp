package com.yeditepe.seventalkapp.data.model

import androidx.compose.runtime.mutableStateListOf

// Gönderi Veri Modeli
// isFavorite: Boolean = false eklendi (Varsayılan olarak favori değil)
data class PostData(
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)

// ORTAK POST KUTUSU
object PostManager {
    val posts = mutableStateListOf(
        PostData("7Talk Valorant odası", "gerçekten rekabetçi işine gönül vermiş...", false),
        PostData("Yeni bir oyun tavsiyesi: Hollow Horizon", "Son zamanlarda oynadığım...", false)
    )

    fun addPost(post: PostData) {
        posts.add(0, post)
    }

    // YENİ: Favori durumunu tersine çeviren fonksiyon
    fun toggleFavorite(post: PostData) {
        val index = posts.indexOf(post)
        if (index != -1) {
            // Veriyi kopyalayıp true ise false, false ise true yapıyoruz
            posts[index] = posts[index].copy(isFavorite = !post.isFavorite)
        }
    }
}