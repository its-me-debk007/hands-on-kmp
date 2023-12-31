package screen

import ColorPrimary
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.aakira.napier.Napier
import model.Result
import util.MovieCard
import viewmodel.SharedViewModel
import viewmodel.UiState

class HomePage : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { SharedViewModel() }
        val uiState by viewModel.getMoviesFlow.collectAsState()
        val navigator = LocalNavigator.current
        var movies by remember { mutableStateOf(emptyList<Result>()) }

        LaunchedEffect(Unit) { viewModel.getMovies() }

        Box(Modifier.fillMaxSize()) {
            Column {
                Text(
                    "Discover",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp)
                )

                AnimatedVisibility(
                    movies.isNotEmpty(),
                    enter = fadeIn() + slideInVertically { 100 },
                    exit = fadeOut() + slideOutVertically { 100 }
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth().padding(8.dp, 16.dp, 8.dp),
                        columns = GridCells.Adaptive(160.dp)
                    ) {
                        items(movies.size) {
                            MovieCard(movies[it], onMovieClick = { movie ->
                                navigator?.push(DetailsPage(movie))
                            })
                        }
                    }
                }


                when (uiState) {
                    is UiState.Loading -> {
                        movies = emptyList()

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(color = ColorPrimary)
                        }
                    }

                    is UiState.Failure -> {
                        val msg = (uiState as UiState.Failure).errorMsg
                        Napier.d { msg }
                    }

                    is UiState.Success -> {
                        movies = (uiState as UiState.Success).data as List<Result>
                        Napier.d { movies.toString() }
                    }
                }
            }

            FloatingActionButton(
                onClick = { navigator?.push(SearchPage()) },
                backgroundColor = ColorPrimary,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp).align(Alignment.BottomEnd)
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Go to Search Page",
                    tint = Color.White
                )
            }
        }
    }
}