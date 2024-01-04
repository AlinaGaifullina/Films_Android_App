package ru.itis.filmsandroidapp.feature.home.impl.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import ru.itis.filmsandroidapp.feature.auth.api.navigation.LoginDestination
import ru.itis.filmsandroidapp.feature.film_details.api.navigation.FilmDetailsDestination
import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.home.impl.R
import ru.itis.filmsandroidapp.feature.profile.api.navigation.ProfileDestination

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(HomeEvent.OnLaunch)
    }

    LaunchedEffect(action) {
        when (action) {
            is HomeSideEffect.NavigateToProfileScreen -> {
                viewModel.navigate(
                    ProfileDestination.createRoute(
                        state.username
                    )
                )
            }
            is HomeSideEffect.NavigateToFilmDetailsScreen -> {
                viewModel.navigate(
                    FilmDetailsDestination.createRoute(
                        (action as HomeSideEffect.NavigateToFilmDetailsScreen).filmId.toString(),
                        state.username
                    )
                )
            }
            HomeSideEffect.NavigateToLoginScreen -> viewModel.navigate(LoginDestination.createLoginRoute())
            else -> Unit
        }
    }

    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Column{
                TextButton(
                    onClick = { eventHandler.invoke(HomeEvent.OnUsernameClick(state.username)) }
                ) {
                    Text(
                        state.username,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }
        }

        val listState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(listState),
            ){
            Text(
                text = stringResource(id = R.string.films_list),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Divider(color = MaterialTheme.colorScheme.primary, thickness = 3.dp)

            if (state.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                    Text(
                        text = stringResource(id = R.string.loading),
                        modifier = Modifier
                            .padding(40.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else if(state.error.isNotEmpty()){
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
                            eventHandler.invoke(HomeEvent.OnLaunch)
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
                Column {
                    state.data.filmsList.forEach { film ->
                        CreateListItem(
                            item = film,
                        ) {
                            eventHandler.invoke(HomeEvent.OnFilmCardClick(it))
                        }
                    }
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(
                            onClick = {
                                eventHandler.invoke(HomeEvent.OnBackPageBtnClick)
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.backPage),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                        Text(
                            text = state.currentPage.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        TextButton(
                            onClick = {
                                eventHandler.invoke(HomeEvent.OnNextPageBtnClick)
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.nextPage),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun CreateListItem(
    item: ShortFilmInfoModel,
    onCardClick: (filmId: Int) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp)
        .clickable { onCardClick(item.filmId) },
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ){
        Column {

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Text(
                    text = item.filmName,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        painter = painterResource(id = ru.itis.filmsandroidapp.core.widget.R.drawable.ic_star),
                        "star",
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = item.rating,
                        modifier = Modifier.padding(start = 10.dp, end = 16.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier.size(500.dp),
                    model = item.posterInfo ,
                    placeholder = painterResource(ru.itis.filmsandroidapp.core.widget.R.drawable.ic_hide),
                    contentDescription = "poster",
                )
            }

            Text(
                text = item.shortDescription,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}