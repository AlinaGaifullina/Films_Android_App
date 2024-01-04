package ru.itis.filmsandroidapp.feature.profile.impl.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ru.itis.filmsandroidapp.feature.film_details.api.navigation.FilmDetailsDestination
import ru.itis.filmsandroidapp.feature.profile.api.model.ShortFilmInfoModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(ProfileEvent.OnLaunch)
    }


    LaunchedEffect(action) {
        when (action) {

            ProfileSideEffect.NavigateBack -> viewModel.navigateUp()
            is ProfileSideEffect.NavigateToFilmDetailsScreen -> {
                viewModel.navigate(
                    FilmDetailsDestination.createRoute(
                        (action as ProfileSideEffect.NavigateToFilmDetailsScreen).filmId.toString(),
                        state.user.username
                    )
                )
            }
            else -> Unit
        }
    }

    val listState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(listState)

    ){

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
                    text = "Loading",
                    modifier = Modifier
                        .padding(40.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        } else {
            if(state.error.isNotEmpty()){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = state.error,
                        modifier = Modifier
                            .padding(top = 40.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                        TextButton(
                            onClick = {
                                eventHandler.invoke(ProfileEvent.OnLaunch)
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        ) {
                            Text(
                                text = "Попробовать снова",//stringResource(id = R.string.try_again),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth()

                ){
                    Icon(
                        painterResource(ru.itis.filmsandroidapp.core.widget.R.drawable.ic_back),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                            .size(40.dp)
                            .clickable { eventHandler.invoke(ProfileEvent.OnBackBtnClick) },
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Профиль",
                        modifier = Modifier.padding(16.dp).align(Alignment.Center),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = "Логин: ${state.user.username}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Имя: ${state.user.firstName}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Фамилия: ${state.user.lastName}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = "Избранное:",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                state.favouritesFilms.forEach {
                    FavouriteFilmItem(
                        it
                    ){
                        eventHandler.invoke(ProfileEvent.OnFilmCardClick(it))
                    }
                }
            }
        }
    }
}

@Composable
fun FavouriteFilmItem(
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
                //horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    model = item.posterInfo ,
                    contentDescription = "poster",
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                ) {
                    Text(
                        text = item.filmName,
                        modifier = Modifier,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = item.shortDescription,
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
