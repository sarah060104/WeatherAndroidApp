package be.he2b.a61601_projet.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import be.he2b.a61601_projet.R

@Composable
fun HomeScreen() {
    Box(
        Modifier.Companion
            .fillMaxSize()
            .background(color = Color.Companion.White),
        contentAlignment = Alignment.Companion.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "LogoESI"
        )
    }
}