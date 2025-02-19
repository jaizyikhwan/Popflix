package com.jaizyikhwan.popflix.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun FeatureFavorite(navController: NavHostController) {
    DynamicFeatureUtils.featureFavorite(navController)
}