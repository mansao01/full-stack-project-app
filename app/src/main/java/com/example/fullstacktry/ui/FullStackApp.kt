package com.example.fullstacktry.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fullstacktry.R
import com.example.fullstacktry.ui.navigation.NavigationItem
import com.example.fullstacktry.ui.navigation.Screen
import com.example.fullstacktry.ui.screen.add.AddScreen
import com.example.fullstacktry.ui.screen.add.AddViewModel
import com.example.fullstacktry.ui.screen.edit.EditScreen
import com.example.fullstacktry.ui.screen.edit.EditViewModel
import com.example.fullstacktry.ui.screen.home.HomeScreen
import com.example.fullstacktry.ui.screen.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullStackApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                HomeScreen(uiState = homeViewModel.uiState, navigateToUpdate ={profileId ->
                    navController.navigate(Screen.Update.createRoute(profileId))
                } )
            }

            composable(Screen.Update.route, arguments = listOf(navArgument("profileId"){
                type = NavType.IntType
            })){ data ->
                val editViewMode: EditViewModel = viewModel(factory = EditViewModel.Factory)
                val profileId = data.arguments?.getInt("profileId") ?: -1
                EditScreen(id = profileId, uiState = editViewMode.uiState, navigateToHome = {
                    navController.navigate(Screen.Home.route)
                })
            }

            composable(Screen.Add.route) {
                val addViewModel: AddViewModel = viewModel(factory = AddViewModel.Factory)
                AddScreen(addViewModel.uiState)
            }
        }
    }

}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
                contentDescription = stringResource(R.string.home)

            ),
            NavigationItem(
                title = stringResource(R.string.add),
                icon = Icons.Default.Add,
                screen = Screen.Add,
                contentDescription = stringResource(R.string.add)

            )
        )
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.primary,
//            contentColor = Color.White
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.screen.route,
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription,
                            tint = Color.White
                        )
                    },
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(text = item.title)
                    },
                    unselectedContentColor = Color.White.copy(0.4f),
                    selectedContentColor = Color.White,

                    )
            }
        }
    }
}