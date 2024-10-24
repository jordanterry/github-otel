package uk.co.jordanterry.otel.github.api

import me.tatarka.inject.annotations.Inject
import okhttp3.Interceptor
import okhttp3.Response

@Inject
internal class AcceptGithubJsonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Accept", "application/vnd.github+json"
            )
            .build()
        return chain.proceed(newRequest)
    }
}