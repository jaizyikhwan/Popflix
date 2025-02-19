package com.jaizyikhwan.popflix.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jaizyikhwan.core.data.Resource
import com.jaizyikhwan.popflix.R
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(filmId: Int, navController: NavHostController) {

    val viewModel = koinViewModel<DetailViewModel>()

    val popularFilmsState by viewModel.popularFilms.collectAsState()
    val nowPlayingFilmsState by viewModel.nowPlayingFilms.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    // Mencari film yang sesuai dengan filmId dari daftar yang ada
    val selectedFilm = (popularFilmsState as? Resource.Success)?.data
        ?.firstOrNull { it.id == filmId } // Membandingkan dengan filmId yang bertipe Int
        ?: (nowPlayingFilmsState as? Resource.Success)?.data
            ?.firstOrNull { it.id == filmId }

    LaunchedEffect(filmId) {
        viewModel.checkFavoriteStatus(filmId)
    }


    if (selectedFilm != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.4f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_back),
                                    contentDescription = "Back",
                                    modifier = Modifier
                                        .size(34.dp)
                                        .padding(8.dp)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 0.dp,
                            start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                            end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    val imageUrl = "https://image.tmdb.org/t/p/original${selectedFilm.posterPath}"

                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = selectedFilm.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(18.dp))
                    )

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star), // Gunakan ikon bintang dari drawable
                                contentDescription = "Star Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = String.format(Locale.getDefault(),"%.1f", selectedFilm.voteAverage),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "(${selectedFilm.voteCount} votes)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Gray
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = selectedFilm.title,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(onClick = {
                                if (isFavorite) {
                                    viewModel.removeFavoriteFilm(selectedFilm)
                                } else {
                                    viewModel.addFavoriteFilm(selectedFilm)
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border),
                                    contentDescription = "Favorite",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )

                        Text(
                            text = "Release Date: ${selectedFilm.releaseDate}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = selectedFilm.overview,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify,
                        )

                    }
                }
            }
        )
        }
    }
