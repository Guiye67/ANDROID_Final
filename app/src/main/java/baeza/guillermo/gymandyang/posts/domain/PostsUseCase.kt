package baeza.guillermo.gymandyang.posts.domain

import baeza.guillermo.gymandyang.posts.data.PostsRepository
import baeza.guillermo.gymandyang.ui.models.Post
import javax.inject.Inject

class PostsUseCase @Inject constructor(private val respository: PostsRepository) {
    suspend operator fun invoke(): List<Post>? {
        return respository.getPosts()
    }
}