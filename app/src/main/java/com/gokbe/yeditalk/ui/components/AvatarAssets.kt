package com.gokbe.yeditalk.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.CrueltyFree
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Extension
import androidx.compose.material.icons.rounded.LocalFlorist
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Toys
import androidx.compose.material.icons.rounded.Workspaces
import androidx.compose.ui.graphics.vector.ImageVector

object AvatarAssets {
    val avatars: List<ImageVector> = listOf(
        Icons.Rounded.Pets,          // Dog (approx)
        Icons.Rounded.LocalFlorist,  // Flower
        Icons.Rounded.Star,          // Star
        Icons.Rounded.Toys,          // Pinwheel (approx)
        Icons.Rounded.CrueltyFree,   // Cat / Animal
        Icons.Rounded.Workspaces,    // Origami / Shape
        Icons.Filled.Favorite,       // Heart
        Icons.Rounded.Eco,           // Frog / Nature
        Icons.Rounded.Extension      // Crown / Puzzle
    )
}
