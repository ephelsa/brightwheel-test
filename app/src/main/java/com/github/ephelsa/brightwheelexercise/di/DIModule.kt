package com.github.ephelsa.brightwheelexercise.di

import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.ephelsa.brightwheelexercise.BuildConfig
import com.github.ephelsa.brightwheelexercise.MainViewModel
import com.github.ephelsa.brightwheelexercise.datasource.RemoteRepoInfoDatasource
import com.github.ephelsa.brightwheelexercise.datasource.RemoteRepoInfoDatasourceImpl
import com.github.ephelsa.brightwheelexercise.remote.GithubRepositoryService
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepository
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// This is the simplest DI - not really fancy but functional
internal object DIModule {

    // ViewModels initializer factory
    fun provideViewModelFactory(
        repoInformationRepository: RepoInformationRepository = provideRepoInformationRepository()
    ) = viewModelFactory {
        addInitializer(MainViewModel::class) {
            MainViewModel(
                repoInformationRepository = repoInformationRepository
            )
        }

        // App other initializers below
    }

    /**
     * [RepoInformationRepository] dependency
     */
    fun provideRepoInformationRepository(
        remoteRepoInfoDatasource: RemoteRepoInfoDatasource = provideRemoteRepoInfoDatasource()
    ): RepoInformationRepository = RepoInformationRepositoryImpl(
        remoteDatasource = remoteRepoInfoDatasource
    )

    /**
     * IODispatcher
     */
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Remote datasource
     */
    fun provideRemoteRepoInfoDatasource(
        ioDispatcher: CoroutineDispatcher = provideIODispatcher(),
        ghRepositoryService: GithubRepositoryService = provideGithubRepositoryService()
    ): RemoteRepoInfoDatasource = RemoteRepoInfoDatasourceImpl(
        dispatcher = ioDispatcher, ghRepositoryService = ghRepositoryService
    )

    /**
     * HttpClient configurations
     */
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor {
            val requestBuilder = it.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.GH_API_KEY}")

            it.proceed(requestBuilder.build())
        }

        return builder.build()
    }

    fun provideRetrofitClient(client: OkHttpClient = provideOkHttpClient()): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

    /**
     * Remote services
     */
    fun provideGithubRepositoryService(retrofit: Retrofit = provideRetrofitClient()): GithubRepositoryService =
        retrofit.create(GithubRepositoryService::class.java)
}