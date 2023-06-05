package baeza.guillermo.gymandyang.posts.data.network

import baeza.guillermo.gymandyang.ui.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PostsClient {
    @GET("/posts/")
    suspend fun getPosts(
        @Header("authorization") token: String
    ): Response<List<Post>>
}