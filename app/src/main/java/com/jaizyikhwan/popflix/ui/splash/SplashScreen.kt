package com.jaizyikhwan.popflix.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaizyikhwan.popflix.R
import com.jaizyikhwan.popflix.ui.main.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(navController: NavHostController) {

    val viewModel = koinViewModel<MainViewModel>()

    val popularFilmsState by viewModel.popularFilms.collectAsState()
    val nowPlayingFilmsState by viewModel.nowPlayingFilms.collectAsState()
    val topRatedFilmsState by viewModel.topRatedFilms.collectAsState()
    val upcomingFilmsState by viewModel.upcomingFilms.collectAsState()

    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    // Cek apakah semua data film sudah dimuat
    val isDataLoaded = remember {
        derivedStateOf {
            popularFilmsState.data?.isNotEmpty() == true && nowPlayingFilmsState.data?.isNotEmpty() == true && topRatedFilmsState.data?.isNotEmpty() == true && upcomingFilmsState.data?.isNotEmpty() == true
        }
    }


    LaunchedEffect(isDataLoaded.value) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = EaseOutExpo)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        // Tunggu loading data sebelum navigasi
        while (!isDataLoaded.value) {
            delay(500) // Cek setiap 500ms
        }
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true } // Hapus Splash dari BackStack
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_project2),
                contentDescription = "Splash Logo",
                modifier = Modifier
                    .scale(scale.value)
                    .alpha(alpha.value)
                    .size(120.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Popflix",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .scale(scale.value)
                    .alpha(alpha.value)
            )
        }

    }
}
