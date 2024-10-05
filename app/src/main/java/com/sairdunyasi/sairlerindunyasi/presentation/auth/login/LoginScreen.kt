    package com.sairdunyasi.sairlerindunyasi.presentation.auth.login

    import CustomButton
    import android.util.Log
    import android.widget.Toast
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.platform.testTag
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import com.sairdunyasi.sairlerindunyasi.R
    import com.sairdunyasi.sairlerindunyasi.presentation.auth.login.components.CustomTextField
    import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes
    import com.sairdunyasi.sairlerindunyasi.presentation.theme.AppTypography

    @Composable
    fun LoginScreen(
        navController: NavController,
        loginViewModel: LoginViewModel = hiltViewModel()
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val loginState by loginViewModel.loginState.collectAsState()
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Şairlerin Dünyası",
                    style = AppTypography.displayLarge,
                    modifier = Modifier.padding(top = 240.dp)
                )
                Text(
                    text = "Giriş Yapmak için mail ve şifrenizi giriniz",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
                    modifier = Modifier.padding(top = 14.dp)
                )

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Mail Adresi",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                        .testTag("emailInputField")
                )

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Şifre",
                    isPassword = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .testTag("passwordInputField")
                )

                Spacer(modifier = Modifier.height(16.dp))



                if (loginState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    CustomButton(
                        onClick = {
                            loginViewModel.login(email, password)
                        },
                        text = "Giriş Yap",
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("loginButton")
                    )
                }

                if (loginState.isSuccess) {

                    LaunchedEffect(Unit) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }

                }

                loginState.error?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .testTag("errorMessage")
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomButton(
                        onClick = { navController.navigate(Routes.REGISTER) },
                        text = "Kayıt Ol",
                        modifier = Modifier.weight(1f)
                    )

                    CustomButton(
                        onClick = { navController.navigate(Routes.RESET_PASSWORD) },
                        text = "Şifremi Unuttum?",
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
