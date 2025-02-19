@file:Suppress("SpellCheckingInspection")

package com.jaizyikhwan.popflix.factory

object DynamicFeaturePackageFactory {

    private const val PACKAGE = "com.jaizyikhwan."

    object FeatureFavorite{
        const val FEATURE_FAVORITE_SCREEN = PACKAGE.plus("feature_favorite.FavoriteFilmScreenKt")
        const val COMPOSE_METHOD_NAME = "FavoriteFilmScreen"
    }
}

