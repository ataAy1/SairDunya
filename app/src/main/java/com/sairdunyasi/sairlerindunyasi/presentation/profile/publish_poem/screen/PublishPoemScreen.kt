import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.component.PublishPoemButton
import com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.event.PublishPoemEvent
import com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.viewmodel.PublishPoemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishPoemScreen(
    onBackClick: () -> Unit,
    viewModel: PublishPoemViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

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
                        "Şiir Yayınla",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8B4513))
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    PublishPoemButton(
                        text = "Yayınla",
                        onClick = {
                            if (title.isNotEmpty() && content.isNotEmpty()) {
                                viewModel.onEvent(PublishPoemEvent.PublishPoem(title, content))
                            } else {
                                Toast.makeText(context, "You cannot publish with empty fields!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 26.dp).padding(bottom = 26.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Şiir Başlığı", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFDFDFD),
                    focusedIndicatorColor = Color(0xFF6200EE),
                    unfocusedIndicatorColor = Color.Gray
                ),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )

            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Şiirinizi Yazınız", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 600.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFDFDFD),
                    focusedIndicatorColor = Color(0xFF6200EE),
                    unfocusedIndicatorColor = Color.Gray,),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )


            Spacer(modifier = Modifier.weight(1f))

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (state.errorMessage != null) {
                Text(text = state.errorMessage ?: "An error occurred", color = Color.Red)
            }

            if (state.isSuccess) {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Şiir Yayınlandı..!", Toast.LENGTH_LONG).show()
                    onBackClick()
                }
            }
        }
    }
}