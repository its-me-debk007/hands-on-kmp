import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.Result
import screen.HomePage

@Composable
fun App() {
    MaterialTheme {
        var movies by remember { mutableStateOf(listOf<Result>()) }
        val repository by remember { mutableStateOf(Repository()) }

        LaunchedEffect(null) {
            movies = repository.getMovies()
        }

        Column(
            Modifier.fillMaxSize().background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomePage(movies)
        }
    }
}

expect fun getPlatformName(): String

expect fun initLogger()