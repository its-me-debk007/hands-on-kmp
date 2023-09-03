package screen

import ColorPrimary
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import model.Result
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import util.MovieCard
import viewmodel.SharedViewModel
import viewmodel.UiState

class SearchPage : Screen {

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        var query by remember { mutableStateOf("") }
        val viewModel = rememberScreenModel { SharedViewModel() }
        val uiState by viewModel.searchMoviesFlow.collectAsState()
        val navigator = LocalNavigator.current
        var movies by remember { mutableStateOf(emptyList<Result>()) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            viewModel.searchMovies("Dragon Ball Super")
        }

        Column {
            Row(Modifier.padding(top = 24.dp, start = 16.dp), verticalAlignment = Alignment.Top) {
                FloatingActionButton(
                    onClick = { navigator?.pop() },
                    backgroundColor = Color.DarkGray.copy(alpha = 0.75f),
                    modifier = Modifier.padding(end = 12.dp).size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Go Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    "Search",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            SearchBar(
                query,
                onQueryChange = { query = it },
                onClear = { query = "" },
                onBtnClick = {
                    if (query.isNotBlank()) {
                        viewModel.searchMovies(query.trim())
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                })

            AnimatedVisibility(
                movies.isNotEmpty(),
                enter = fadeIn() + slideInVertically { 100 },
                exit = fadeOut() + slideOutVertically { 100 }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(8.dp, 16.dp, 8.dp),
                ) {
                    items(movies.size) {
                        MovieCard(
                            movies[it],
                            isFullLengthCard = true,
                            onMovieClick = { movie ->
                                navigator?.push(DetailsPage(movie))
                            })
                    }
                }
            }

            when (uiState) {
                is UiState.Loading -> {
                    movies = emptyList()
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(color = ColorPrimary)
                    }
                }

                is UiState.Failure -> {}

                is UiState.Success -> {
                    movies = (uiState as UiState.Success).data as List<Result>
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun SearchBar(
        query: String,
        onQueryChange: (String) -> Unit,
        onClear: () -> Unit,
        onBtnClick: () -> Unit
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                placeholder = { Text("Search Movies", fontSize = 14.sp, color = Color.Gray) },
                modifier = Modifier.weight(3f),
                colors = TextFieldDefaults.textFieldColors(
                    Color.White,
                    cursorColor = ColorPrimary,
                    focusedIndicatorColor = ColorPrimary,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                trailingIcon = {
                    if (query.isNotEmpty()) Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Clear query",
                        tint = Color.White,
                        modifier = Modifier.clickable { onClear() }
                    )
                },
                shape = RoundedCornerShape(8.dp),
                keyboardActions = KeyboardActions { onBtnClick() },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )

            FloatingActionButton(
                onClick = onBtnClick,
                modifier = Modifier.padding(start = 16.dp),
                backgroundColor = ColorPrimary
            ) {
                Image(painterResource("ic_arrow_forward.xml"), contentDescription = "Search Button")
            }
        }
    }
}