package screen

import DarkOrange
import IMG_BASE_URL
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Result
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomePage(movies: List<Result>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(8.dp, 16.dp, 8.dp),
        columns = GridCells.Adaptive(168.dp)
    ) {
        items(movies.size) { MovieCard(movies[it]) }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MovieCard(movie: Result, modifier: Modifier = Modifier) {
    Card(modifier.padding(8.dp, 0.dp, 8.dp, 16.dp), shape = RoundedCornerShape(10.dp)) {
        Box(Modifier.fillMaxWidth()) {
            KamelImage(
                asyncPainterResource(IMG_BASE_URL + "/w300" + movie.poster_path),
                contentDescription = movie.title,
                contentScale = ContentScale.FillBounds,
                onLoading = { _ ->
                    Image(
                        painterResource("placeholder.jpg"),
                        null,
                        Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                },
                animationSpec = tween(),
                modifier = Modifier.height(220.dp)
            )

            Text(
                "${movie.vote_average.toString().substring(0, 3)}⭐",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DarkOrange,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                    .padding(6.dp, 4.dp)
            )

            Column(
                Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black)
                    )
                ).padding(top = 24.dp)
            ) {
                Text(
                    movie.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Row(
                    Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        movie.release_date,
                        maxLines = 2,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                    Text(
                        "(${movie.original_language})",
                        maxLines = 2,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }
            }
        }
    }
}