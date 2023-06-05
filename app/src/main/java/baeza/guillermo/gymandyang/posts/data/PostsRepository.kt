package baeza.guillermo.gymandyang.posts.data

import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.posts.data.network.PostsService
import baeza.guillermo.gymandyang.ui.models.Post
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val api: PostsService,
    private val userPreferenceService: UserPreferenceService
) {
    suspend fun getPosts(): List<Post>? {
        val token = userPreferenceService.getUser("user")!!.token
        return api.getPosts(token)
    }
}