package be.he2b.a61601_projet


import androidx.compose.runtime.getValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.he2b.a61601_projet.data.model.Repository
import be.he2b.a61601_projet.ui.AppScreen
import be.he2b.a61601_projet.ui.theme._61601projetTheme
import be.he2b.a61601_projet.ui.about.AboutScreen
import be.he2b.a61601_projet.ui.home.HomeScreen
import be.he2b.a61601_projet.ui.search.Searching
import be.he2b.a61601_projet.ui.connect.ConnectingRoute



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repository.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            _61601projetTheme {
                val navController = rememberNavController()
                var connected by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar ={
                        if (connected) {

                            NavigationBar {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentRoute = navBackStackEntry?.destination?.route
                                    val screens = listOf(AppScreen.Home, AppScreen.About, AppScreen.Search)
                                    screens.forEach { screen ->
                                        val selected = currentRoute == screen.name
                                        NavigationBarItem(
                                            onClick = {
                                                if (currentRoute != screen.name) {
                                                    navController.navigate(screen.name) {
                                                        popUpTo(navController.graph.startDestinationId) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                            },
                                            selected = selected,
                                            icon = {
                                                Icon(
                                                    imageVector = when (screen) {
                                                        AppScreen.Home -> Icons.Default.Home
                                                        AppScreen.About -> Icons.Default.Info
                                                        AppScreen.Search -> Icons.Default.Search
                                                        else -> Icons.Default.Star
                                                    },
                                                    contentDescription = "Home"
                                                )
                                            }
                                        )
                                    }
                                }

                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.Connection.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = AppScreen.Connection.name) {
                            ConnectingRoute(
                                onNavigateHome = {
                                    connected = true
                                    navController.navigate(AppScreen.Home.name) {
                                        popUpTo(AppScreen.Connection.name) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                        composable(route = AppScreen.Home.name) { HomeScreen() }
                        composable(route = AppScreen.About.name) { AboutScreen() }
                        composable(route = AppScreen.Search.name) { Searching()


                        }}
                    }

                }
            }
        }
    }




