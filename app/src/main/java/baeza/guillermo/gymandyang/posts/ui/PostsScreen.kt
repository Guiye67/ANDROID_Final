package baeza.guillermo.gymandyang.posts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baeza.guillermo.gymandyang.providers.Constants.IP_ADDRESS
import baeza.guillermo.gymandyang.ui.models.Post
import baeza.guillermo.gymandyang.ui.theme.BackgroundGray
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import coil.compose.AsyncImage

@Composable
fun PostsScreen(scaffoldState: ScaffoldState, postsViewModel: PostsViewModel) {
    val posts:List<Post> by postsViewModel.posts.observeAsState(initial = listOf())
    val singlePost:Post? by postsViewModel.singlePost.observeAsState(initial = null)

    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        postsViewModel.getPosts()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundGray)
                    .padding(5.dp)
            ) {
                if (singlePost == null) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Posts",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MainPruple,
                            textDecoration = TextDecoration.Underline
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = { Text("Search title or muscle") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                            },
                            modifier = Modifier.fillMaxWidth(0.8f)
                                .border(
                                    3.dp,
                                    DarkPruple,
                                    RoundedCornerShape(35.dp)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                posts.forEach { post ->
                                    if (searchText.isEmpty()
                                        || post.title.lowercase().contains(searchText.lowercase())
                                        || post.muscle.lowercase().contains(searchText.lowercase())
                                    ) {
                                        PostCard(post = post) { postsViewModel.onCardClick(it) }
                                        Spacer(modifier = Modifier.height(5.dp))
                                    }
                                }
                            }
                        }
                    }
                } else {
                    SinglePostView(post = singlePost!!) { postsViewModel.clearSingleView() }
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostCard(post: Post, onCardClick: (Post) -> Unit) {
    var textSize by remember { mutableStateOf(20.sp) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        elevation = 10.dp,
        onClick = { onCardClick(post) }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
                    .width(70.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "${IP_ADDRESS}posts/img/${post.images}",
                    contentDescription = "Post Image",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, DarkPruple, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.65f)
                    .padding(start = 5.dp, end = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Title", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = post.title,
                    fontSize = textSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1

                        if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                            textSize = textSize.times(0.9f)
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "Muscle", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = post.muscle,
                    fontSize = textSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1

                        if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                            textSize = textSize.times(0.9f)
                        }
                    }
                )
            }
        }
    }
}