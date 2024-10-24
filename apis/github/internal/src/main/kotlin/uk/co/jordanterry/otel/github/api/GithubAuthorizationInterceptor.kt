package uk.co.jordanterry.otel.github.api

import me.tatarka.inject.annotations.Inject
import okhttp3.Interceptor
import okhttp3.Response
import uk.co.jordanterry.internal.BuildConfig

@Inject
internal class GithubAuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.GITHUB_API_KEY}"
            )
            .build()
        return chain.proceed(newRequest)
    }
}