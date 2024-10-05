package com.sairdunyasi.sairlerindunyasi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.NavGraph
import com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.component.BottomNavigationBar
import com.sairdunyasi.sairlerindunyasi.utils.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFirebaseAuth()
        checkNetworkConnection()

        setContent {
            MainContent()
        }
    }

    private fun setupFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

    private fun checkNetworkConnection() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showToast("Internet bağlantısı yok. Lütfen ayarları kontrol edin.")
            finish()
        }
    }

    @Composable
    private fun MainContent() {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                Column(modifier = Modifier.fillMaxSize()) {
                    NavGraph(navController = navController, modifier = Modifier.weight(1f))
                    BottomNavigationBar(navController = navController)
                    checkUserAndNavigate(navController)
                }
            }
        }
    }

    private fun checkUserAndNavigate(navController: NavController) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
