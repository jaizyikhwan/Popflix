package com.jaizyikhwan.popflix.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jaizyikhwan.popflix.ui.about.AboutScreen
import com.jaizyikhwan.popflix.ui.detail.DetailScreen
import com.jaizyikhwan.popflix.ui.main.MainScreen
import com.jaizyikhwan.popflix.ui.splash.SplashScreen
import com.jaizyikhwan.popflix.utils.FeatureFavorite

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("home") {
            MainScreen(navController)
        }
        composable(route = "favorite") {
            FeatureFavorite(navController)
        }
        composable("detail/{filmId}", arguments = listOf(navArgument("filmId") { type = NavType.IntType })) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getInt("filmId") ?: 0
            DetailScreen(filmId = filmId, navController = navController)
        }
        composable(route = "about") {
            AboutScreen(navController)
        }
    }
}

