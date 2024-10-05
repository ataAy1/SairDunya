package com.sairdunyasi.sairlerindunyasi.presentation.auth.register

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sairdunyasi.sairlerindunyasi.R
import com.sairdunyasi.sairlerindunyasi.presentation.auth.register.components.ButtonRegister
import com.sairdunyasi.sairlerindunyasi.presentation.auth.register.components.RegisterTextField
import com.sairdunyasi.sairlerindunyasi.presentation.auth.register.components.RegisterTopBar
import com.sairdunyasi.sairlerindunyasi.presentation.auth.register.event.RegisterEvent
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.viewmodel.ProfileViewModel
import com.sairdunyasi.sairlerindunyasi.presentation.theme.AppTypography

@Composable
fun RegisterScreen(navController: NavController, profileViewModel: RegisterViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val state by profileViewModel.registerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when {
            state.isSuccess -> {
                Toast.makeText(context, "Başarılı Kayıt!", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.REGISTER) { inclusive = true }
                }
            }
            state.error != null -> {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            RegisterTopBar(navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.register_bg),
                    contentDescription = "Register Icon",
                    modifier = Modifier.size(144.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(26.dp))

                RegisterTextField(
                    value = nickname,
                    onValueChange = { nickname = it.trim() },
                    placeholder = "Kullanıcı Adı",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterTextField(
                    value = email,
                    onValueChange = { email = it.trim() },
                    placeholder = "Mail Adresi",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Şifre",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Şifre Onay",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    ButtonRegister(
                        onClick = {
                            errorMessage = when {
                                nickname.isEmpty() -> "Kullanıcı adınızı giriniz."
                                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Mail Adresinizi Kontrol Ediniz"
                                password.isEmpty() -> "Şifrenizi Giriniz."
                                password != confirmPassword -> "Şifreler Eşleşmedi."
                                else -> {
                                    profileViewModel.onEvent(RegisterEvent.Submit(email, nickname, password))
                                    ""
                                }
                            }
                        },
                        text = "Kayıt Ol",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = {
                        navController.navigate(Routes.LOGIN)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hesabın var mı? Giriş yap!")
                }
            }
        }
    )
}
