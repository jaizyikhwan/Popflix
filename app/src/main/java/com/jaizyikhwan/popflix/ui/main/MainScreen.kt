package com.jaizyikhwan.popflix.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.jaizyikhwan.core.data.Resource
import com.jaizyikhwan.core.domain.model.Film
import com.jaizyikhwan.popflix.R
import com.jaizyikhwan.popflix.ui.component.BottomNavigationBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController
) {

    val viewModel = koinViewModel<MainViewModel>()

    val popularFilmsState by viewModel.popularFilms.collectAsState()
    val nowPlayingFilmsState by viewModel.nowPlayingFilms.collectAsState()
    val topRatedFilmsState by viewModel.topRatedFilms.collectAsState()
    val upcomingFilmsState by viewModel.upcomingFilms.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Scaffold(
        topBar = { TopBar(viewModel) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

                if (searchQuery.isNotEmpty()) {
                    // Tampilkan hasil pencarian jika ada query
                    SearchResults(searchResults, navController)
                } else {
                    // Tampilkan daftar film jika tidak sedang mencari
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        item { NowPlayingFilmSection(nowPlayingFilmsState, navController) }
                        item { FilmSection("Popular Movies", popularFilmsState, navController) }
                        item { FilmSection("Top Rated Movies", topRatedFilmsState, navController) }
                        item { FilmSection("Upcoming Movies", upcomingFilmsState, navController) }
                    }
                }
        }

    }
}

@Composable
fun TopBar(viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_project2),
            contentDescription = "Logo",
            modifier = Modifier.height(32.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Popflix",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.weight(1f))
        SearchField(viewModel)
    }
}

@Composable
fun NowPlayingFilmSection(filmState: Resource<List<Film>>, navController: NavHostController) {
    when (filmState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            filmState.data?.let { films ->
                Column {
                    Text(
                        text = "Now Playing Movies",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(films) { film ->
                            NowPlayingFilmItem(film, navController)
                        }
                    }
                }
            }
        }
        is Resource.Error -> {
            Text(
                text = "Error: ${filmState.message}"
            )
        }
    }
}

@Composable
fun NowPlayingFilmItem(film: Film, navController: NavHostController) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${film.backdropPath}"
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - 32.dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(screenWidth)
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(12.dp))
            .clickable { navController.navigate("detail/${film.id}") }
    ) {
        // Background Blur
        Card(
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)) // Clip agar gambar tidak keluar dari Card
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(100.dp),
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Gradient Overlay
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter),
            onDraw = {
                drawRect(
                    Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)))
                )
            }
        )

        // Movie Details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(2f / 3f),
                shape = RoundedCornerShape(12.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = imageUrl,
                    contentDescription = film.title,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = film.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                text = film.releaseDate.take(4),
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FilmSection(title: String, filmState: Resource<List<Film>>, navController: NavHostController) {
    when (filmState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            filmState.data?.let { films ->
                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    FilmList(films, navController)
                }
            }
        }
        is Resource.Error -> {
            Text(
                text = "Error: ${filmState.message}"
            )
        }
    }
}

@Composable
fun FilmList(films: List<Film>, navController: NavHostController) {
    LazyRow(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = film.title,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
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

@Composable
fun SearchField(viewModel: MainViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth(0.65f)
            .height(32.dp) // Sesuaikan tinggi agar lebih kecil
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp) // Tambahkan padding agar tidak terlalu mepet
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            singleLine = true,
            textStyle = TextStyle(fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                "Search films...",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

@Composable
fun SearchResults(searchResults: List<Film>, navController: NavHostController) {
    if (searchResults.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No results found",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp, max = 600.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults) { film ->
                FilmItem(film, navController)
            }
        }
    }
}
