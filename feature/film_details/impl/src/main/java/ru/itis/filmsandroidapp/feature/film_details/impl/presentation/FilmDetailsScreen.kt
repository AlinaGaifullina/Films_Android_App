package ru.itis.filmsandroidapp.feature.film_details.impl.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ru.itis.filmsandroidapp.feature.film_details.impl.R


@Composable
fun FilmDetailsScreen(
    viewModel: FilmDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(FilmDetailsEvent.OnLaunch)
    }

    LaunchedEffect(action) {
        when (action) {
            FilmDetailsSideEffect.NavigateBack -> viewModel.navigateUp()
            else -> Unit
        }
    }
    val listState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(listState)
    ) {
        if (state.isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                    strokeWidth = 4.dp
                )
                Text(
                    text = stringResource(id = R.string.loading),
                    modifier = Modifier
                        .padding(40.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        if(state.error.isNotEmpty()){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.default_error),
                    modifier = Modifier
                        .padding(top = 40.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
                TextButton(
                    onClick = {
                        eventHandler.invoke(FilmDetailsEvent.OnLaunch)
                    },
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.try_again),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        painterResource(ru.itis.filmsandroidapp.core.widget.R.drawable.ic_back),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                            .size(28.dp)
                            .clickable { eventHandler.invoke(FilmDetailsEvent.OnBackBtnClick) },
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Icon(
                        painterResource(ru.itis.filmsandroidapp.core.widget.R.drawable.ic_favourite),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                            .size(30.dp)
                            .clickable { eventHandler.invoke(FilmDetailsEvent.OnFavouriteChange) },
                        contentDescription = "favourite",
                        tint = if (state.isFavouriteFilm)
                            MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface
                    )
                }

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 22.dp, bottom = 8.dp),
                    text = state.data.filmName ?: " ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier,
                        model = state.data.posterInfo.posterPreviewUrl ,
                        contentDescription = "poster",
                    )
                    Divider(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), thickness = 3.dp, color = MaterialTheme.colorScheme.secondary)
                }


                Row (
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.rating_kp),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = state.data.rating.ratingKp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Row (
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(id = R.string.rating_imdb),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = state.data.rating.ratingImdb,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(id = R.string.description),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = state.data.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

}