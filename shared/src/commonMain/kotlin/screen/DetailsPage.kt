package screen

import Background
import DarkOrange
import IMG_BASE_URL
import LightGray
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Result

@Composable
fun DetailsPage(movie: Result) {
    val imgHeight = 300.dp

    Box(Modifier.fillMaxSize()) {
        KamelImage(
            asyncPainterResource("$IMG_BASE_URL/w500${movie.backdrop_path}"),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            onLoading = { ImagePlaceholder() },
            onFailure = { ImagePlaceholder() },
            modifier = Modifier.fillMaxWidth().height(imgHeight)
        )

        Box(
            Modifier.fillMaxWidth().height(imgHeight)
                .background(Brush.verticalGradient(listOf(Color.Transparent, Background)))
        )

        Column(
            Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            KamelImage(
                asyncPainterResource("$IMG_BASE_URL/w500${movie.poster_path}"),
                contentDescription = movie.title,
                contentScale = ContentScale.FillBounds,
                onLoading = { ImagePlaceholder() },
                onFailure = { ImagePlaceholder() },
                modifier = Modifier.padding(top = 128.dp).size(200.dp, 280.dp)
            )

            Text(
                movie.original_title,
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp, start = 16.dp, end = 16.dp)
            )

            ExtraInfo("Original Language:", movie.original_language)
            ExtraInfo("English Title:", movie.title)
            ExtraInfo("Rating:", "${movie.vote_average.toString().substring(0, 3)} ⭐")
            ExtraInfo("Overview:", movie.overview, false)

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun ExtraInfo(subHeading: String, value: String, isRow: Boolean = true) {
    val modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp)
    if (isRow) Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        ExtraContent(subHeading, value, isRow)
    }
    else Column(modifier) { ExtraContent(subHeading, value, isRow) }
}

@Composable
fun ExtraContent(subHeading: String, value: String, isRow: Boolean) {
    Text(
        subHeading,
        color = Color.White,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
    )

    Text(
        value,
        color = if (subHeading != "Rating:") LightGray else DarkOrange,
        fontSize = 14.sp,
        fontWeight = if (subHeading != "Rating:") FontWeight.Normal else FontWeight.SemiBold,
        modifier = Modifier.padding(
            if (isRow) PaddingValues(8.dp, 0.dp, 0.dp, 0.dp)
            else PaddingValues(0.dp, 8.dp, 0.dp, 0.dp)
        )
    )
}

