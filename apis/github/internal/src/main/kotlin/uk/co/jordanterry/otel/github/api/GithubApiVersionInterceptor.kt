package uk.co.jordanterry.otel.github.api

import me.tatarka.inject.annotations.Inject
import okhttp3.Interceptor
import okhttp3.Response

@Inject
internal class GithubApiVersionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "X-GitHub-Api-Version", "2022-11-28"
            )
            .build()
        return chain.proceed(newRequest)
    }
}