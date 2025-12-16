package com.example.a7talkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a7talkapp.ui.theme._7TalkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _7TalkAppTheme {
                // İşte burayı değiştirdik: Artık bizim hazırladığımız ana ekranı çağırıyoruz.
                MainScreen()
            }
        }
    }
}