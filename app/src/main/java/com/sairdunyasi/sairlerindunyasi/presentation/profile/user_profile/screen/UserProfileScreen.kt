package com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sairdunyasi.sairlerindunyasi.R
import com.sairdunyasi.sairlerindunyasi.data.utils.ImageUtils
import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.component.UserProfileBackground
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.events.ProfileEvent
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.state.ProfileState
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onBackClick: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.profileState.collectAsState()
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(Unit) {
        profileViewModel.onEvent(ProfileEvent.LoadProfile)
    }

    Scaffold(
        topBar = { UserProfileTopBar(onBackClick) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            UserProfileBackground()
            when (profileState) {
                is ProfileState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ProfileState.Success -> {
                    val profileData = (profileState as ProfileState.Success).profileData
                    UserProfileContent(profileData, profileViewModel, context, screenWidth)
                }
                is ProfileState.Error -> {
                    Toast.makeText(context, (profileState as ProfileState.Error).errorMessage, Toast.LENGTH_SHORT).show()
                }
                is ProfileState.UpdateSuccess -> {
                    Toast.makeText(context, "Başarılı", Toast.LENGTH_SHORT).show()
                    profileViewModel.onEvent(ProfileEvent.LoadProfile)
                }
                is ProfileState.Updating -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileTopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Profil Ayarları",
                color = Color.Black,
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Back",
                    tint = Color.Unspecified
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.background(Color.Transparent)
    )
}

@Composable
fun UserProfileContent(
    profileData: ProfileModel,
    profileViewModel: ProfileViewModel,
    context: Context,
    screenWidth: Dp
) {
    var nickname by remember { mutableStateOf(profileData.sairNicki) }

    val imageOffset = if (screenWidth < 400.dp) 20.dp else 8.dp
    val imageSize = if (screenWidth < 400.dp) 180.dp else 200.dp
    val spacerHeight = if (screenWidth < 400.dp) 8.dp else 36.dp
    val roundSpace = if (screenWidth < 400.dp) 100.dp else 170.dp
    val verticalSpace = if (screenWidth < 400.dp) 3.dp else 8.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(verticalSpace)
    ) {
        ProfileImage(profileData, imageSize, imageOffset, roundSpace)

        ChangePhotoButton(profileViewModel)

        Spacer(modifier = Modifier.height(spacerHeight))

        UserEmail(profileData.usermail)

        Spacer(modifier = Modifier.height(10.dp))

        UserNicknameField(nickname) { newNickname ->
            nickname = newNickname
        }

        Spacer(modifier = Modifier.height(10.dp))

        UpdateButton(nickname, profileData.sairNicki) {
            profileViewModel.onEvent(ProfileEvent.UpdateProfile(nickname))
        }
    }
}

@Composable
fun ProfileImage(profileData: ProfileModel, imageSize: Dp, imageOffset: Dp, roundSpace: Dp) {
    Box(
        modifier = Modifier
            .size(imageSize)
            .background(Color(0xFF75564C), shape = RoundedCornerShape(roundSpace))
            .padding(imageOffset),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = ImageUtils.loadImage(
                (profileData.dowloandUri ?: R.drawable.ic_profile_photo).toString()
            ),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ChangePhotoButton(profileViewModel: ProfileViewModel) {
    val imagePickerLauncher = ImageUtils.rememberImagePickerLauncher { uri ->
        uri?.let {
            profileViewModel.onEvent(ProfileEvent.UpdateProfilePhoto(it))
        }
    }

    IconButton(onClick = {
        imagePickerLauncher.launch("image/*")
    }) {
        Icon(
            painter = painterResource(id = R.drawable.change_photo_icon),
            contentDescription = "Change Photo",
            tint = Color.Unspecified,
            modifier = Modifier.size(120.dp)
        )
    }
    Text(
        text = "Profil Fotoğrafı Değiştir",
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

@Composable
fun UserEmail(userEmail: String) {
    Text(
        text = "Şairin Maili",
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .background(Color(0xFFADD8E6), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
    OutlinedTextField(
        value = userEmail,
        onValueChange = {},
        enabled = false,
        label = { Text("Şair Maili") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        ),
        shape = MaterialTheme.shapes.small,
    )
}

@Composable
fun UserNicknameField(nickname: String, onNicknameChange: (String) -> Unit) {
    Text(
        text = "Şairin Nicki",
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .background(Color(0xFFADD8E6), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
    OutlinedTextField(
        value = nickname,
        onValueChange = onNicknameChange,
        label = { Text("Kullanıcı Adı") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    )
}

@Composable
fun UpdateButton(nickname: String, currentNickname: String, onUpdateClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            if (nickname != currentNickname) {
                onUpdateClick()
            } else {
                Toast.makeText(context, "Başka bir kullanıcı adı deneyiniz.", Toast.LENGTH_SHORT).show()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00B8D4)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Güncelle",
            color = Color.Black,
            fontSize = 26.sp
        )
    }
}
