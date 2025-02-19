package com.jaizyikhwan.popflix.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.jaizyikhwan.popflix.R


@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            label = { Text(text = "Home") },
            icon = { Icon(painter = rememberAsyncImagePainter(model = R.drawable.ic_home), contentDescription = null) },
            onClick = {
                navController.navigate("home") {
                    popUpTo("home")
                    launchSingleTop = true
                }
                      },
        )
        NavigationBarItem(
            selected = currentRoute == "favorite",
            onClick = {
                navController.navigate("favorite") {
                    popUpTo("favorite")
                    launchSingleTop = true
                }
                      },
            label = { Text(text = "Favorite") },
            icon = { Icon(painter = rememberAsyncImagePainter(model = R.drawable.ic_favorite_filled), contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentRoute == "about",
            onClick = {
                navController.navigate("about") {
                    popUpTo("about")
                    launchSingleTop = true
                }
            },
            label = { Text(text = "About") },
            icon = { Icon(painter = rememberAsyncImagePainter(model = R.drawable.ic_about), contentDescription = null) }
        )
    }
}