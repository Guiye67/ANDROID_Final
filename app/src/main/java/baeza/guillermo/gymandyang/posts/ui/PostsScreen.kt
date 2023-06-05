package baeza.guillermo.gymandyang.posts.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import baeza.guillermo.gymandyang.ui.models.Post

@Composable
fun PostsScreen(scaffoldState: ScaffoldState, postsViewModel: PostsViewModel) {
    val posts:List<Post> by postsViewModel.posts.observeAsState(initial = listOf())

    LaunchedEffect(true) {
        postsViewModel.getPosts()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    posts.forEach {
                        Text(text = it.title)
                    }
                }
            }
        }
    )
}