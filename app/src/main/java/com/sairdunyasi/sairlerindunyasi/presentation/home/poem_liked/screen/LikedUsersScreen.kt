import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.sairdunyasi.sairlerindunyasi.domain.model.LikedUserModel
import com.sairdunyasi.sairlerindunyasi.presentation.home.poem_liked.viewmodel.LikedUsersViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedUsersScreen(poemId: String, navController: NavHostController) {
    val viewModel: LikedUsersViewModel = hiltViewModel()

    LaunchedEffect(poemId) {
        viewModel.fetchLikedUsers(poemId)
    }

    val state by viewModel.likedUsersState.collectAsState()

    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState(
       true,
    )

    LaunchedEffect(Unit) {
        scope.launch {
            bottomSheetState.show()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                bottomSheetState.hide()
                navController.popBackStack()
            }
        },
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Şiiri Beğenenler",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(modifier = Modifier.weight(1f)) {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    val usersList = state.users.collectAsState(initial = emptyList())

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(usersList.value) { user ->
                            UserRow(user)
                        }
                    }
                }


                state.error?.let {
                    Text("Error: $it", color = MaterialTheme.colorScheme.error)
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                CloseScreenButton(
                    onClick = {
                        scope.launch {
                            bottomSheetState.hide()
                            navController.popBackStack()
                        }
                    },
                    text = "Kapat"
                )
            }
        }
    }
}

@Composable
fun UserRow(user: LikedUserModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF553A19)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberImagePainter(data = user.userProfilePhoto),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = user.userNick,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(start = 6.dp)
            )
        }
    }
}

@Composable
fun CloseScreenButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFDDD8D3),
    textColor: Color = Color.Black
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}
