package com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.sairdunyasi.sairlerindunyasi.R
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes
import com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.viewmodel.HomeViewModel
import com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.events.HomeEvents
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePoemsScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    onPoemClick: (String, List<PoemWithProfileModel>) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val favoriteCount = state.poems.count { it.isFavorite }
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = if (screenWidth < 400.dp) 114.dp else 140.dp
    val arrowSpace = if (screenWidth < 400.dp) 90.dp else 158.dp

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {

            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color(0xF2F8F0F0))
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {

            val currentPoem =
                state.currentPoem ?: state.poems.firstOrNull()
            val currentIndex = state.poems.indexOf(currentPoem)
            val totalPoems = state.poems.size

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 30.dp)
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                currentPoem?.let { poem ->
                                    viewModel.onEvent(HomeEvents.OnFavoriteToggle(poem.id))
                                }
                            }) {
                                Icon(
                                    imageVector = if (currentPoem?.isFavorite == true) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (currentPoem?.isFavorite == true) "Unfavorite" else "Favorite",
                                    tint = if (currentPoem?.isFavorite == true) Color.Red else Color.Gray

                                )
                            }

                            Text(
                                text = "${currentPoem?.counterFavNumber ?: 0}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .clickable {
                                        if (currentPoem?.counterFavNumber ?: 0 > 0) {
                                            navController.navigate("liked_screen/${currentPoem?.id}")
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Beğenen Yok !",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    }
                            )


                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        currentPoem?.let {

                            Text(
                                text = it.userNick,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF8B4513),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        val userPoems =
                                            state.poems.filter { poem -> poem.userNick == it.userNick }
                                        onPoemClick(it.userNick, userPoems)
                                    }
                                    .padding(
                                        horizontal = 22.dp,
                                        vertical = 8.dp
                                    )
                            )

                            Spacer(modifier = Modifier.height(18.dp))


                            val imageUrl = it.userProfilePhoto
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFFFCFAFA),
                                        shape = CircleShape
                                    )
                            ) {
                                Image(
                                    painter = rememberImagePainter(imageUrl),
                                    contentDescription = "User Profile Photo",
                                    modifier = Modifier
                                        .size(imageSize)
                                        .clip(CircleShape)
                                        .border(
                                            4.dp,
                                            Color(0xFF8B4513),
                                            CircleShape
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    currentPoem?.let {
                        Text(
                            text = it.siirBasligi,
                            color = Color(0xFF8B4513),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        currentPoem?.let {
                            Text(
                                text = it.siir,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 26.dp),
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = arrowSpace)
                    ) {

                        if (currentIndex > 0) {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(HomeEvents.OnPreviousPoemClick)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Geri",
                                    tint = Color(0xFF8B4513)
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(48.dp))
                        }

                        if (currentIndex < totalPoems - 1) {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(HomeEvents.OnNextPoemClick)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = "İleri",
                                    tint = Color(0xFF8B4513)
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(48.dp))
                        }
                    }


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.1f)
                                    ),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            )
                    )
                }


            }
        }
    }
}
