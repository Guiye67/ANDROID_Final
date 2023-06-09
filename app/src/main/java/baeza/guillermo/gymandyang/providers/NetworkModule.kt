package baeza.guillermo.gymandyang.providers

import baeza.guillermo.gymandyang.classes.data.network.ClassesClient
import baeza.guillermo.gymandyang.diets.data.network.DietsClient
import baeza.guillermo.gymandyang.home.data.network.HomeClient
import baeza.guillermo.gymandyang.login.data.network.LoginClient
import baeza.guillermo.gymandyang.posts.data.network.PostsClient
import baeza.guillermo.gymandyang.profile.data.network.ProfileClient
import baeza.guillermo.gymandyang.providers.Constants.IP_ADDRESS
import baeza.guillermo.gymandyang.register.data.network.RegisterClient
import baeza.guillermo.gymandyang.suggestions.data.network.SuggestionsClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            request.addHeader("Accept", "application/json")
            val actualRequest = request.build()
            it.proceed(actualRequest)
        }
    }

    @Singleton
    @Provides
    fun getHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(IP_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient).build()
    }

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit): LoginClient {
        return retrofit.create(LoginClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterClient(retrofit: Retrofit): RegisterClient {
        return retrofit.create(RegisterClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileClient(retrofit: Retrofit): ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }

    @Singleton
    @Provides
    fun provideDietsClient(retrofit: Retrofit): DietsClient {
        return retrofit.create(DietsClient::class.java)
    }

    @Singleton
    @Provides
    fun provideSuggestionsClient(retrofit: Retrofit): SuggestionsClient {
        return retrofit.create(SuggestionsClient::class.java)
    }

    @Singleton
    @Provides
    fun providePostsClient(retrofit: Retrofit): PostsClient {
        return retrofit.create(PostsClient::class.java)
    }

    @Singleton
    @Provides
    fun provideClassesClient(retrofit: Retrofit): ClassesClient {
        return retrofit.create(ClassesClient::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeClient(retrofit: Retrofit): HomeClient {
        return retrofit.create(HomeClient::class.java)
    }
}