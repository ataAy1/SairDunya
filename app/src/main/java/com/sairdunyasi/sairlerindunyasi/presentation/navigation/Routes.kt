package com.sairdunyasi.sairlerindunyasi.presentation.navigation


object Routes {
    // auth
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val RESET_PASSWORD = "reset_password"

    // profile
    const val USER_PROFILE = "user_profile"
    const val ACTION = "action"
    const val PUBLISHED_LIST = "published_list"
    const val PUBLISHED_POEM_DETAIL = "publish_poem_detail/{poemJson}"
    const val PUBLISH_POEM = "publish_poem"

    // home
    const val HOME = "home"
    const val POEMS_LIST = "poems_list/{userNick}?poems={poems}"
    const val POEM_DETAIL = "poem_detail/{poemId}/{poemTitle}/{poemContent}"
    const val HOME_POEMS = "home_poems"
    const val LIKED_SCREEN = "liked_screen/{poemId}"

    // main
    const val MAIN = "main"

}
