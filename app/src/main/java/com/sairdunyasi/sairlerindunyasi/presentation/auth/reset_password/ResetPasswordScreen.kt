import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sairdunyasi.sairlerindunyasi.R
import com.sairdunyasi.sairlerindunyasi.presentation.auth.register.RegisterViewModel
import com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password.ResetPasswordViewModel
import com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password.components.ResetButton
import com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password.components.ResetPasswordTopBar
import com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password.event.ResetEvent
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(navController: NavController, viewModel: ResetPasswordViewModel = hiltViewModel()) {

    val resetState by viewModel.resetState.collectAsState()
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(resetState) {
        if (resetState.successMessage != null) {
            Toast.makeText(context, resetState.successMessage, Toast.LENGTH_LONG).show()
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.RESET_PASSWORD) { inclusive = true }
            }
        } else if (resetState.errorMessage != null) {
            Toast.makeText(context, resetState.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            ResetPasswordTopBar(navController = navController)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Transparent)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))

                Icon(
                    painter = painterResource(id = R.drawable.register_bg),
                    contentDescription = "Email Icon",
                    tint = Color.Blue,
                    modifier = Modifier.size(214.dp).padding(bottom = 40.dp)
                )

                Spacer(modifier = Modifier.height(44.dp))

                EmailInput(
                    email = email,
                    onEmailChange = { email = it.trim() },
                    Modifier.padding(bottom = 20.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                ResetButton(
                    onClick = {
                        viewModel.onEvent(ResetEvent.Submit(email))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    text = if (resetState.isLoading) "Lütfen Bekleyin..." else "Şifremi Sıfırla"
                )
            }
        }
    )
}
