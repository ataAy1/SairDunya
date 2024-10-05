package com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sairdunyasi.sairlerindunyasi.presentation.navigation.Routes

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == Routes.HOME || currentRoute == Routes.ACTION) {
        NavigationBar(
            modifier = Modifier
                .height(56.dp)
                .background(Color(0xFF8B4513)),
            containerColor = Color(0xFF552808),
        ) {

            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Anasayfa") },
                label = { Text("Anasayfa") },
                selected = currentRoute == Routes.HOME,
                onClick = {
                    if (currentRoute != Routes.HOME) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color.Transparent
                )
            )


            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profilim") },
                label = { Text("Profilim") },
                selected = currentRoute == Routes.ACTION,
                onClick = {
                    if (currentRoute != Routes.ACTION) {
                        navController.navigate(Routes.ACTION) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color.Transparent

                )
            )
        }
    }
}
