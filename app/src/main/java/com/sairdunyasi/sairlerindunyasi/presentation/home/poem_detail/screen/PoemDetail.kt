package com.sairdunyasi.sairlerindunyasi.presentation.home.poem_detail.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoemDetailScreen(
    onBackClick: () -> Unit,
    poemTitle: String,
    poemContent: String
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7F7))
    ) {
        TopAppBar(
            title = { AppBarTitle() },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8B4513))
        )

        Spacer(modifier = Modifier.height(6.dp))

        PoemContent(
            scrollState = scrollState,
            title = poemTitle,
            content = poemContent
        )
    }
}

@Composable
fun AppBarTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Şiir Detayı",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(end = 50.dp)
        )
    }
}

@Composable
fun PoemContent(scrollState: ScrollState, title: String, content: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PoemTitle(title)
            PoemBody(content)
        }
    }
}

@Composable
fun PoemTitle(title: String) {
    Text(
        text = title,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF890047),
        modifier = Modifier
            .background(Color(0xFFF5EEEE))
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun PoemBody(content: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color(0xFF2C0D1D), shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        color = Color(0xFFFFFCFC)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = content,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}
