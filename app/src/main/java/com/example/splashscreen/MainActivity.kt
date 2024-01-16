package com.example.splashscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.splashscreen.ui.theme.SplashScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigation = rememberNavController()
                    NavHost(
                        navController = navigation,
                        startDestination = Routes.SplashScreen.route
                    ) {
                        composable(Routes.SplashScreen.route) { SplashScreen(navigation) }
                        composable(Routes.MenuScreen.route) { MenuScreen(navigation) }
                        composable(
                            Routes.Pantalla1.route,
                            arguments = listOf(
                                navArgument("dificultad") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            Screen1(navigation, backStackEntry.arguments?.getString("dificultad").orEmpty()
                            )
                        }
                        composable(
                            Routes.Pantalla2.route,
                            arguments = listOf(
                                navArgument("ganar") { type = NavType.BoolType },
                                navArgument("intentos") { type = NavType.IntType },
                                navArgument("dificultad") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            Screen2(
                                navigation,
                                backStackEntry.arguments?.getBoolean("ganar") ?: true ,
                                backStackEntry.arguments?.getInt("intentos") ?: 0 ,
                                backStackEntry.arguments?.getString("dificultad").orEmpty()
                            )
                        }


                    }

                }
            }
        }
    }
}
/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SplashScreenTheme {
        Greeting("Android")
    }
}

 */