package baeza.guillermo.gymandyang.posts.data.network

import android.util.Log
import baeza.guillermo.gymandyang.ui.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostsService @Inject constructor(
    private val postsClient: PostsClient
) {
    suspend fun getPosts(token: String): List<Post>? {
        return withContext(Dispatchers.IO) {
            val response = postsClient.getPosts(token)

            Log.i("GYM", "data: ${response.body()}")

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}