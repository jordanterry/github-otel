package uk.co.jordanterry.otel.github.api.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import uk.co.jordanterry.otel.github.api.AcceptGithubJsonInterceptor
import uk.co.jordanterry.otel.github.api.GithubApiVersionInterceptor
import uk.co.jordanterry.otel.github.api.GithubAuthorizationInterceptor

@Component
public abstract class OkHttpComponent {

    @Provides
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    internal fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        acceptGithubJsonInterceptor: AcceptGithubJsonInterceptor,
        githubApiVersionInterceptor: GithubApiVersionInterceptor,
        githubAuthorizationInterceptor: GithubAuthorizationInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(acceptGithubJsonInterceptor)
            .addInterceptor(githubApiVersionInterceptor)
            .addInterceptor(githubAuthorizationInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


}