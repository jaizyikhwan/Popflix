package com.jaizyikhwan.feature_favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jaizyikhwan.core.domain.model.Film
import com.jaizyikhwan.popflix.ui.component.BottomNavigationBar
import com.jaizyikhwan.popflix.ui.favorite.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

/*
 * this class is used by reflection
 */


@Suppress("unused")
@Composable
fun FavoriteFilmScreen(navController: NavHostController) {

    val viewModel = koinViewModel<FavoriteViewModel>()

    val favoriteFilmsState by viewModel.favoriteFilms.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favoriteFilmsState.isEmpty()) {
                Text(
                    text = "No favorite movies yet",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                FilmList(favoriteFilmsState, navController)
            }

        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Favorite Movies",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FilmList(films: List<Film>, navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(films) { film ->
            FilmItem(film, navController)
        }
    }
}

@Composable
fun FilmItem(film: Film, navController: NavHostController) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${film.posterPath}"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(120.dp)
            .clickable {
                navController.navigate("detail/${film.id}")
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = film.title,
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = film.title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.heightIn(min = 28.dp)
        )
    }
}
