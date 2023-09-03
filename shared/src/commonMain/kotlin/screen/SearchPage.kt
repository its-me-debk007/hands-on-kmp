package screen

import ColorPrimary
import Repository
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import model.Result
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchPage(repository: Repository) {
    var query by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf(listOf<Result>()) }
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(
            "Search",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, start = 24.dp)
        )

        SearchBar(query, onQueryChange = { query = it }, onClear = { query = "" }, onBtnClick = {
            if (query.isNotBlank()) {
                coroutineScope.launch {
                    movies = repository.searchMovies(query.trim())
                    Napier.d { movies[0].toString() }
                }
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
                items(movies.size) { MovieCard(movies[it], isFullLengthCard = true) }
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
//                backgroundColor = Color.DarkGray,
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

        FloatingActionButton(onClick = onBtnClick, modifier = Modifier.padding(start = 16.dp)) {
            Image(painterResource("ic_arrow_forward.xml"), contentDescription = "Search Button")
        }
    }
}