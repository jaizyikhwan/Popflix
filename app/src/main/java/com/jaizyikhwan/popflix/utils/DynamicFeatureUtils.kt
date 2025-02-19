package com.jaizyikhwan.popflix.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.navigation.NavHostController
import com.jaizyikhwan.popflix.factory.DynamicFeaturePackageFactory
import com.jaizyikhwan.popflix.ui.shownotfound.ShowNotFoundScreen

object DynamicFeatureUtils {

    @Composable
    fun featureFavorite(navController: NavHostController): Boolean {
        return loadFeatureFavorite(
            navController = navController,
            className = DynamicFeaturePackageFactory.FeatureFavorite.FEATURE_FAVORITE_SCREEN,
            methodName = DynamicFeaturePackageFactory.FeatureFavorite.COMPOSE_METHOD_NAME
        )
    }

    @Composable
    private fun loadFeatureFavorite(
        navController: NavHostController,
        className: String,
        methodName: String,
        objectInstance: Any = Any()
    ): Boolean {
        val favoriteClass = loadClassByReflection(className)
        if (favoriteClass != null) {
            val composer = currentComposer
            val method = findMethodByReflection(
                favoriteClass,
                methodName
            )
            if (method != null) {
                val isMethodInvoked =
                    invokeMethod(method, objectInstance, navController, composer, 0)
                if (!isMethodInvoked) {
                    ShowNotFoundScreen()
                    return false
                }
                return true
            } else {
                ShowNotFoundScreen()
                return false
            }
        } else {
            ShowNotFoundScreen()
            return false
        }
    }

}