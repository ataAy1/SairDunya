package com.sairdunyasi.sairlerindunyasi.presentation.home.poems_list.screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import com.sairdunyasi.sairlerindunyasi.presentation.home.poems_list.state.PoemsListState
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes
import java.net.URLEncoder
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoemsListScreen(userNick: String, navController: NavController, state: PoemsListState) {
    Log.d("PoemsListScreen", "Received poems: ${state.poems}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7F7))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Şairin Yayınladığı Şiirler",
                    fontSize = 24.sp,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8B4513))
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(state.poems) { poem ->
                PoemItem(poem = poem) {
                    val encodedPoemTitle = URLEncoder.encode(poem.siirBasligi, "UTF-8")
                    val encodedPoemContent = URLEncoder.encode(poem.siir, "UTF-8")
                    val navigationUri = "poem_detail/${poem.id}/$encodedPoemTitle/$encodedPoemContent"

                    Log.d("Navigation", "Navigating to: $navigationUri")

                    navController.navigate(navigationUri)
                }
            }
        }



    }
}


@Composable
fun PoemItem(poem: PoemWithProfileModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF3F3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = poem.siirBasligi,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = poem.date,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    IconButton(
                        onClick = {
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Beğeni Sayısı",
                            tint = if (poem.isFavorite) Color.Red else Color.Gray
                        )
                    }
                    Text(
                        text = poem.counterFavNumber.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0EAEA), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = poem.siir,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go to details",
                    tint = Color(0xFF0288D1),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
