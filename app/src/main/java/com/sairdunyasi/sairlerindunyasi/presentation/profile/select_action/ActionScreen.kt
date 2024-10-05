package com.sairdunyasi.sairlerindunyasi.presentation.profile.select_action

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sairdunyasi.sairlerindunyasi.R
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes
import com.sairdunyasi.sairlerindunyasi.presentation.theme.AppTypography

@Composable
fun ActionScreen(
    navController: NavController,
    viewModel: ActionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            HeaderSection()

            ActionGrid(navController, viewModel, context)
        }
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bg_topheader),
            contentDescription = "Background Header",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_action),
            contentDescription = "Icon",
            modifier = Modifier
                .align(Alignment.Center)
                .size(110.dp)
                .padding(14.dp),
            tint = Color.White
        )
    }
}

@Composable
fun ActionGrid(navController: NavController, viewModel: ActionViewModel, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionCard(
                imageResId = R.drawable.icon_siir_yayin,
                text = "Şiir Yayınla",
                modifier = Modifier.weight(1f)
            ) { navController.navigate(Routes.PUBLISH_POEM) }

            ActionCard(
                imageResId = R.drawable.profille_icon,
                text = "Profilim",
                modifier = Modifier.weight(1f)
            ) { navController.navigate(Routes.USER_PROFILE) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionCard(
                imageResId = R.drawable.poetry_icon,
                text = "Şiirlerim",
                modifier = Modifier.weight(1f)
            ) { navController.navigate(Routes.PUBLISHED_LIST) }

            ActionCard(
                imageResId = R.drawable.ic_close,
                text = "Çıkış Yap",
                modifier = Modifier.weight(1f)
            ) {
                viewModel.logout()
                Toast.makeText(context, "Çıkış Yapıldı ... ", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.LOGIN)
            }
        }
    }
}

@Composable
fun ActionCard(imageResId: Int, text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardHeight = if (screenWidth < 400.dp) 190.dp else 260.dp

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDFB)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = text,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = text,
                color = Color(0xFFD32F2F),
                style = AppTypography.displayMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}