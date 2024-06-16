package com.fit.movies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.fit.core.common.LoadingImage
import com.fit.core.common.NoConnectionImage
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.model.MovieModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AllCategoriesScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AllCategoriesScreen(viewModel: MoviesViewModel) {
    val uiModel by viewModel.uiModel.collectAsState()

    when (val uiModelValue = uiModel) {
        MoviesViewModel.UIModel.Loading -> {
            LoadingImage()
        }

        MoviesViewModel.UIModel.NoConnection -> {
            NoConnectionImage(onClick = {
                viewModel.fetchMovies()
            })
        }

        is MoviesViewModel.UIModel.ShowView -> {
            BodyTabMovie(uiModelValue.categoryModel)
        }
    }
}

@Composable
fun BodyTabMovie(categories: List<CategoryModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}

@Composable
fun CategoryItem(category: CategoryModel) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.h6.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )
        LazyRow {
            items(category.movies) { movie ->
                MovieItem(movie = movie)
            }
        }
    }

}

@Composable
fun MovieItem(movie: MovieModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(150.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}