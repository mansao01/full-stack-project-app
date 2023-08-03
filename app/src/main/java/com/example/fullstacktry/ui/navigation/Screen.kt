package com.example.fullstacktry.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("Add")
    object About : Screen("about")
    object Update : Screen("home/{profileId}") {
        fun createRoute(profileId: Int) = "home/$profileId"
    }
}