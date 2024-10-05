package com.sairdunyasi.sairlerindunyasi.presentation.profile.published

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.sairdunyasi.sairlerindunyasi.R
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.component.PublishedList
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.events.PublishedPoemEvent
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.state.PublishedPoemState
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.viewmodel.PublishedPoemViewModel
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.viewmodel.ProfileViewModel



import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishedListScreen(
    onBackClick: () -> Unit,
    onDetailClick: (PoemModel) -> Unit,
    viewModel: PublishedPoemViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is PublishedPoemState.DeletionSuccess) {
            Toast.makeText(context, "Şiir Silindi!", Toast.LENGTH_SHORT).show()
        }
    }


    LaunchedEffect(Unit) {
        viewModel.onEvent(PublishedPoemEvent.FetchPoems)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Arrow",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(
                        "Yayınladığım Şiirler",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8B4513))
            )
        }
    ) { paddingValues ->
        when (state) {
            is PublishedPoemState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
            }
            is PublishedPoemState.Success -> {
                PublishedList(
                    poems = state.poems,
                    paddingValues = paddingValues,
                    onEvent = { event ->
                        when (event) {
                            is PublishedPoemEvent.DeletePoem -> {
                                viewModel.onEvent(event)
                            }
                            is PublishedPoemEvent.DetailPoem -> {
                                onDetailClick(event.poemModel)
                            }
                            else -> Unit
                        }
                    },
                    context = context
                )
            }
            is PublishedPoemState.Error -> {
                Text("Error: ${state.message}", modifier = Modifier.padding(paddingValues))
            }

            PublishedPoemState.DeletionSuccess -> {

            }        }
    }
}
