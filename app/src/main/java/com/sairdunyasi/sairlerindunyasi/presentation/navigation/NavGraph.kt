package com.sairdunyasi.sairlerindunyasi.presentation.navigation

import LikedUsersScreen
import PublishPoemScreen
import ResetPasswordScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.sairdunyasi.sairlerindunyasi.MainActivity
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import com.sairdunyasi.sairlerindunyasi.presentation.auth.login.LoginScreen
import com.sairdunyasi.sairlerindunyasi.presentation.auth.register.RegisterScreen
import com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.screen.HomePoemsScreen
import com.sairdunyasi.sairlerindunyasi.presentation.home.poem_detail.screen.PoemDetailScreen
import com.sairdunyasi.sairlerindunyasi.presentation.home.poems_list.screen.PoemsListScreen
import com.sairdunyasi.sairlerindunyasi.presentation.home.poems_list.state.PoemsListState
import com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem_detail.screen.PublishedPoemDetailScreen
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published.PublishedListScreen
import com.sairdunyasi.sairlerindunyasi.presentation.profile.select_action.ActionScreen
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.UserProfileScreen
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.LOGIN, modifier = modifier) {
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.REGISTER) { RegisterScreen(navController) }
        composable(Routes.RESET_PASSWORD) { ResetPasswordScreen(navController) }
        composable(Routes.MAIN) { MainActivity() }

        // Home Screen
        composable(Routes.HOME) {
            HomePoemsScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                onPoemClick = { userNick, poems ->

                    val userPoems = poems.filter { it.userNick == userNick }

                    val userPoemsJson = Json.encodeToString(ListSerializer(PoemWithProfileModel.serializer()), userPoems)

                    val encodedUserPoemsJson = URLEncoder.encode(userPoemsJson, "UTF-8")

                    val cleanedUserNick = userNick.trim()

                    navController.navigate("${Routes.POEMS_LIST}/$cleanedUserNick?poems=$encodedUserPoemsJson")
                }
            )
        }
        composable(
            Routes.POEMS_LIST,
            arguments = listOf(
                navArgument("userNick") { type = NavType.StringType },
                navArgument("poems") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userNick = backStackEntry.arguments?.getString("userNick")
            val poemsJson = backStackEntry.arguments?.getString("poems")?.let {
                val decoded = URLDecoder.decode(it, "UTF-8")
                val startIndex = decoded.indexOf("[")
                if (startIndex != -1) {
                    decoded.substring(startIndex)
                } else {
                    null
                }
            }


            val poems = if (poemsJson != null && poemsJson.startsWith("[")) {
                try {
                    Json.decodeFromString(ListSerializer(PoemWithProfileModel.serializer()), poemsJson)
                } catch (e: Exception) {
                    emptyList()
                }
            } else {
                emptyList()
            }

            userNick?.let {
                PoemsListScreen(userNick = it, navController = navController, state = PoemsListState(poems = poems))
            }
        }
        
        composable(
            Routes.POEM_DETAIL,
            arguments = listOf(
                navArgument("poemId") { type = NavType.StringType },
                navArgument("poemTitle") { type = NavType.StringType },
                navArgument("poemContent") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val poemId = backStackEntry.arguments?.getString("poemId")
            val poemTitle = backStackEntry.arguments?.getString("poemTitle")?.let {
                URLDecoder.decode(it, "UTF-8")
            }
            val poemContent = backStackEntry.arguments?.getString("poemContent")?.let {
                URLDecoder.decode(it, "UTF-8")
            }

            if (poemId != null && poemTitle != null && poemContent != null) {
                PoemDetailScreen(
                    onBackClick = { navController.popBackStack() },
                    poemTitle = poemTitle,
                    poemContent = poemContent
                )
            }
        }

        // profile
        composable(Routes.ACTION) { ActionScreen(navController) }

        composable(Routes.USER_PROFILE) {
            UserProfileScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Routes.PUBLISHED_LIST) {
            PublishedListScreen(
                onBackClick = { navController.popBackStack() },
                onDetailClick = { poem ->
                    val poemJson = Gson().toJson(poem)
                    val encodedPoemJson = URLEncoder.encode(poemJson, "UTF-8")
                    navController.navigate("${Routes.PUBLISHED_POEM_DETAIL}/$encodedPoemJson")
                }
            )
        }

        composable("${Routes.PUBLISHED_POEM_DETAIL}/{poemJson}") { backStackEntry ->
            val encodedPoemJson = backStackEntry.arguments?.getString("poemJson") ?: ""
            val poemJson = URLDecoder.decode(encodedPoemJson, "UTF-8")
            val poem = Gson().fromJson(poemJson, PoemModel::class.java)
            PublishedPoemDetailScreen(poem = poem, onBackClick = { navController.popBackStack() })
        }

        composable(Routes.PUBLISH_POEM) {
            PublishPoemScreen(onBackClick = { navController.popBackStack() })
        }

        composable(
            Routes.LIKED_SCREEN,
            arguments = listOf(navArgument("poemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val poemId = backStackEntry.arguments?.getString("poemId")
            if (poemId != null) {
                LikedUsersScreen(poemId = poemId, navController = navController)
            }
        }
    }
}

