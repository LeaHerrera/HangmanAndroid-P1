package com.example.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Splash(alphaAnim: Float) {
    Column(modifier = Modifier
        .paint( //fondo
            painterResource(id = R.drawable.fondo),
            contentScale = ContentScale.FillBounds
        )
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo", alpha = alphaAnim
        )
        Text(text = "¡Escapa de la muerte!", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

    }
}
