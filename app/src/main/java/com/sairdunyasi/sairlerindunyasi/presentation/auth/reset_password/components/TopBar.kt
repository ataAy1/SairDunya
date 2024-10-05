package com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.sairdunyasi.sairlerindunyasi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Şİfre Sıfırlama",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Back",
                    tint = Color.Unspecified
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF9CD4DA)

        )
    )
}
