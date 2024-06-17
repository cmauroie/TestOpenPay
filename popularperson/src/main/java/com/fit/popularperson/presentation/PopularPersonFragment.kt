package com.fit.popularperson.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.fit.core.common.LoadingImage
import com.fit.core.common.NoConnectionImage
import com.fit.popularperson.R
import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.presentation.ui.theme.Gray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularPersonFragment : Fragment() {

    private val viewModel: PopularPersonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiModel by viewModel.uiModel.collectAsState()
                when (val uiModelValue = uiModel) {
                    PopularPersonViewModel.UIModel.Loading -> {
                        LoadingImage()
                    }

                    PopularPersonViewModel.UIModel.NoConnection -> {
                        NoConnectionImage(onClick = {
                            viewModel.fetchMovies()
                        })
                    }

                    is PopularPersonViewModel.UIModel.ShowView -> {
                        BodyTabPopular(uiModelValue.profileModel)
                    }
                }
            }
        }
    }
}

@Composable
fun BodyTabPopular(profileModel: ProfileModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieCard(movie = profileModel)
        Spacer(modifier = Modifier.size(10.dp))
        Text("known for", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.size(10.dp))
        MovieGrid(movies = profileModel.knownFor)
    }
}

@Composable
fun MovieCard(movie: ProfileModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(),
        elevation = 8.dp,
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(model = movie.profilePath),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = movie.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                RankWithIcon(movie.popularity.toString())
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = movie.allTitleMovies,
                    color = Gray,
                    style = MaterialTheme.typography.body2,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RankWithIcon(rankingText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_star_rate_24),
            contentDescription = "Ranking Icon"
        )
        Text(
            text = rankingText,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun MovieGrid(movies: List<KnownForModel>) {
    LazyRow {
        items(movies) { movie ->
            KnownForCard(movie = movie)
        }
    }
}

@Composable
fun KnownForCard(movie: KnownForModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(340.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = movie.posterPath),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .width(300.dp)
                )
                RankWithIcon(movie.popularity.toString())
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = movie.originalTitle,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.overview,
                    color = Gray,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}