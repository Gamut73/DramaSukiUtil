package org.artificery.dramasukiutil.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.artificery.dramasukiutil.presentation.component.MovieDetailListComponent
import org.artificery.dramasukiutil.presentation.model.MainScreenUIState
import org.artificery.dramasukiutil.presentation.viewmodel.MainViewModel
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinInject(),
) {
    val uiState by viewModel.uIState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("DramaSuki Util")
                }
            )
        }
    ) { innerPadding ->
        when(uiState) {
            is MainScreenUIState.Search -> {
                UrlEntryComponent(
                    onSearch = { url ->
                        viewModel.getMovieDataFromUrl(url)
                    },
                )
            }
            is MainScreenUIState.Loading -> {
                LoadingComponent("Extracting movie data...")
            }
            is MainScreenUIState.MovieDataSuccess -> {
                val movieDataList = (uiState as MainScreenUIState.MovieDataSuccess).movies
                if (movieDataList.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(movieDataList) { movieData ->
                            MovieDetailListComponent(data = movieData)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                } else {
                    Text(text = "No movie data found.")
                }
            }
        }
    }

}

@Composable
fun UrlEntryComponent(
    onSearch: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter URL") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { onSearch(text) }
            ) {
                Text("Search")
            }
        }
    }
}

@Composable
fun LoadingComponent(
    message: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            if (!message.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = message)
            }
        }
    }
}