package uk.co.jordanterry.otel.github.api.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import retrofit2.Retrofit
import uk.co.jordanterry.otel.github.api.GithubActionsService
import uk.co.jordanterry.otel.github.api.GithubApi

@Component
public abstract class GithubApiComponent(
    @Component public val retrofitComponent: RetrofitComponent,
) {

    @Provides
    internal fun githubActionsService(retrofit: Retrofit): GithubActionsService =
        retrofit.create(GithubActionsService::class.java)

    public abstract val githubApi: GithubApi
}