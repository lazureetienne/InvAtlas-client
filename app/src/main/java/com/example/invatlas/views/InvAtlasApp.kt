package com.example.invatlas.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.invatlas.Level
import com.example.invatlas.R
import com.example.invatlas.navigation.InvAtlasDestinations
import com.example.invatlas.ui.theme.AppTheme
import com.example.invatlas.viewmodels.PlantViewModel

sealed class Screen(val route: String, val name: String, val icon: Int) {
    object map : Screen(InvAtlasDestinations.MAP_ROUTE, "Atlas", R.drawable.outline_map_24)
    object chat : Screen(InvAtlasDestinations.CHAT_ROUTE, "Ivy", R.drawable.outline_forum_24)
    object pokedex : Screen(InvAtlasDestinations.FLORADEX_ROUTE, "Floradex", R.drawable.outline_yard_24)

}

@Composable
fun InvAtlasApp() {
    val vm = PlantViewModel()
    var bottomBarVisible by remember { mutableStateOf(false) }
    fun createLevels(): List<Level> {
        val levels = mutableListOf<Level>()
        for (i in 1..100) {
            levels.add(Level(i, 10 * i))
        }
        return levels
    }

    val levels = createLevels()

    fun getUserLevel(xp: Int): Int {
        val userLevel = levels.find { it.xpCap > xp } ?: levels.last()
        return userLevel.level
    }

    fun xpCap(userLevel: Int): Int {
        return levels.find { it.level == userLevel }?.xpCap ?: 0
    }

    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { _, destination, _ ->
        bottomBarVisible = when(destination.route) {
            InvAtlasDestinations.MAP_ROUTE, InvAtlasDestinations.CHAT_ROUTE, InvAtlasDestinations.FLORADEX_ROUTE -> {
                true
            } else -> {
                false
            }
        }
    }


    val items = listOf(
        Screen.map,
        Screen.pokedex,
    )

    AppTheme {
        Scaffold(
            topBar = {
                if (bottomBarVisible) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 15.dp),
                                text = "Niveau ${getUserLevel(vm.sessionUser?.xp ?: 0)}"
                            )
                            val progress by animateFloatAsState(
                                targetValue = vm.sessionUser?.xp?.toFloat()
                                    ?: 0f / xpCap(getUserLevel(vm.sessionUser?.xp ?: 0)),
                                label = ""
                            )
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                modifier = Modifier.padding(start = 15.dp),
                                text = "${vm.sessionUser?.xp ?: 0}/${xpCap(getUserLevel(vm.sessionUser?.xp ?: 0))} xp"
                            )
                        }
                    }
                }
            },
            bottomBar = {
                if (bottomBarVisible) {
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colorScheme.primary
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(painterResource(screen.icon), screen.name) },
                                label = { Text(screen.name) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
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
                            )
                        }
                    }
                }

            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = InvAtlasDestinations.SIGN_UP_ROUTE,
                Modifier.padding(innerPadding)
            ) {
                composable(InvAtlasDestinations.SIGN_UP_ROUTE) { SignUpScreen(vm, navController) }
                composable(InvAtlasDestinations.MAP_ROUTE) { AtlasScreen(vm, navController) }
                composable(
                    InvAtlasDestinations.CHAT_ROUTE,
                    arguments = listOf(navArgument("plantCode") { type = NavType.StringType })
                ) { navBackStackEntry -> IvyScreen(vm,
                    navBackStackEntry.arguments?.getString("plantCode")!!,
                    navBackStackEntry.arguments?.getString("plantName")!!
                ) }
                composable(InvAtlasDestinations.FLORADEX_ROUTE) { FloradexScreen(vm, navController) }
            }

        }
    }
}


@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}
