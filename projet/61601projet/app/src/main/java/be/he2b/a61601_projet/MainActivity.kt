package be.he2b.a61601_projet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.he2b.a61601_projet.ui.theme._61601projetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _61601projetTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.Connection.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = AppScreen.Connection.name) {
                            ConnectingRoute(
                                onNavigateHome = {
                                    navController.navigate(AppScreen.Home.name) {
                                        popUpTo(AppScreen.Connection.name) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(route = AppScreen.Home.name) { HomeScreen() }
                    }

                }
            }
        }
    }
}



