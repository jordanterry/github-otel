package uk.co.jordanterry.otel.github.api.interceptors

import okhttp3.Interceptor
import uk.co.jordanterry.internal.BuildConfig

@Suppress("FunctionName")
public fun GithubAuthorizationInterceptor() : Interceptor =
    Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.GITHUB_API_KEY}"
            )
            .build()
        chain.proceed(newRequest)
    }