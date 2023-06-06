package baeza.guillermo.gymandyang.posts.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baeza.guillermo.gymandyang.providers.Constants
import baeza.guillermo.gymandyang.ui.models.Post
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainRed
import coil.compose.AsyncImage

@Composable
fun SinglePostView(post: Post, onBackClick: () -> Unit) {
    var textSize by remember { mutableStateOf(24.sp) }

    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "${Constants.IP_ADDRESS}posts/img/${post.images}",
                    contentDescription = "Post Image",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, DarkPruple, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.65f)
                        .padding(end = 10.dp),
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
                        .fillMaxWidth(),
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "Description", fontSize = 16.sp, color = Color.Gray)
                Text(text = post.description, textAlign = TextAlign.Justify)
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                Button(
                    onClick = { onBackClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MainRed),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back Icon",
                        tint = Color.White
                    )
                    Text(text = "Back", color = Color.White)
                }
            }
        }
    }
}