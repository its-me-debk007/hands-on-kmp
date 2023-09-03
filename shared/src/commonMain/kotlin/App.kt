import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import model.Result
import screen.DetailsPage

@Composable
fun App() {
    MaterialTheme {
        var movies by remember { mutableStateOf(listOf<Result>()) }
        val repository by remember { mutableStateOf(Repository()) }
        val movie by remember {
            mutableStateOf(
                Result(
                    adult = false,
                    backdrop_path = "/daeD5T4MxFk5MppZlaNZoaK6lE9.jpg",
                    id = 811948,
                    original_language = "ja",
                    original_title = "鬼滅の刃 柱合会議・蝶屋敷編",
                    overview = "Tanjiro and his sister Nezuko have been apprehended by the Demon Slayer Hashira, a group of extremely skilled swordfighters. Tanjiro undergoes trial for violating the Demon Slayer code, specifically smuggling Nezuko, a Demon, onto Mt. Natagumo. A recap film of Kimetsu no Yaiba, covering episodes 22-26 with some new footage and special ending credits.",
                    popularity = 102.357,
                    poster_path = "/iTmJwZxGHYAk5EiVc68UvZTMSuP.jpg",
                    release_date = "2021-04-15",
                    title = "Demon Slayer: Kimetsu no Yaiba the Hashira Meeting Arc",
                    video = true,
                    vote_average = 7.151,
                    vote_count = 186
                )
            )
        }

        LaunchedEffect(null) {
            movies = repository.getMovies()
        }

        Surface(color = Background, modifier = Modifier.fillMaxSize()) {
            DetailsPage(movie)
        }
    }
}

expect fun getPlatformName(): String

expect fun initLogger()