package uk.co.jordanterry.otel.github.api

import GithubInterceptorComponent
import create
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import uk.co.jordanterry.otel.services.okhttp.OkHttpGraph
import uk.co.jordanterry.otel.services.retrofit.RetrofitComponent
import uk.co.jordanterry.otel.services.retrofit.RetrofitGraph

@Component
public abstract class GithubApiComponent(
    @Component public val retrofitComponent: RetrofitComponent,
) {

    @Provides
    public fun provideGithubApi(githubActionsService: GithubActionsService): GithubApi =
        DefaultGithubApi(githubActionsService)

    @Provides
    internal fun githubActionsService(retrofit: Retrofit): GithubActionsService =
        retrofit.create(GithubActionsService::class.java)

    public abstract val githubApi: GithubApi
}

@Suppress("FunctionName")
public fun GithubApiGraph(
    url: HttpUrl = "https://api.github.com/".toHttpUrl(),
): GithubApiComponent {
    val githubInterceptorComponent = GithubInterceptorComponent::class.create()
    val okHttpComponent = OkHttpGraph(githubInterceptorComponent.interceptors)
    val retrofitComponent = RetrofitGraph(
        okHttpComponent = okHttpComponent,
        url = url,
    )
    return GithubApiComponent::class.create(
        retrofitComponent
    )
}