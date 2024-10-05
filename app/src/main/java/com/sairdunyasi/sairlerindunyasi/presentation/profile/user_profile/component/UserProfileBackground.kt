package com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun UserProfileBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp)
                .offset(y = (-150).dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0CB96B), Color(0xFF20B9CC)),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 300f)
                    ),
                    shape = RectangleShape
                )
        )
    }
}
