package org.artificery.dramasukiutil.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.artificery.dramasukiutil.data.repository.MovieData

@Composable
fun MovieDetailListComponent(data: MovieData) {
    with(data) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Row {
                AsyncImage(
                    model = data.posterUrl,
                    contentDescription = "Movie Poster",
                    modifier = Modifier.padding(16.dp)
                        .width(150.dp)
                        .height(300.dp),
                )
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SelectionContainer {
                        Text(
                            text = "Title: $title",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Text(
                        text = "Year: ${year ?: "N/A"}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Rating: ${rating ?: "N/A"}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Description: ${description ?: "No description available"}")
                    if (alternativeTitle != null) {
                        Text(text = "Alternative Title: $alternativeTitle")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieDetailListComponentPreview() {
    val sampleData = MovieData(
        title = "Sample Movie",
        year = 2023,
        alternativeTitle = "Sample Alternative Title",
        description = "This is a sample movie description.",
        posterUrl = "https://example.com/sample-poster.jpg",
        rating = 8.5f
    )
    MovieDetailListComponent(data = sampleData)
}