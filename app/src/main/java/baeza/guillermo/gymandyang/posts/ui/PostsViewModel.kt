package baeza.guillermo.gymandyang.posts.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.posts.domain.PostsUseCase
import baeza.guillermo.gymandyang.ui.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _singlePost = MutableLiveData<Post>()
    val singlePost: LiveData<Post> = _singlePost

    fun getPosts() {
        _loading.value = true
        viewModelScope.launch {
            _posts.value = postsUseCase()
            Log.i("GYM", "posts: ${_posts.value}")
            _loading.value = false
        }
    }

    fun onCardClick(post: Post) {
        _singlePost.value = post
    }

    fun clearSingleView() {
        _singlePost.value = null
    }
}