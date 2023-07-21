package com.example.testcompose

import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val Name:String,
    val route:String,
    val icon: ImageVector,
    val badgeCount:Int=0
)
