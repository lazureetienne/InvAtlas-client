package com.example.invatlas.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object InvAtlasDestinations {
    const val MAP_ROUTE = "map"
    const val SIGN_UP_ROUTE = "signup"
    const val CHAT_ROUTE = "chat"
    const val FLORADEX_ROUTE = "floradex"
}

/**
 * Models the navigation actions in the app.
 */
class InvAtlasNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(InvAtlasDestinations.MAP_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToInterests: () -> Unit = {
        navController.navigate(InvAtlasDestinations.MAP_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}