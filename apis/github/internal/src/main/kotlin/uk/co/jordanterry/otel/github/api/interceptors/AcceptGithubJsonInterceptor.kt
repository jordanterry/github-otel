package uk.co.jordanterry.otel.github.api.interceptors

import okhttp3.Interceptor

@Suppress("FunctionName")
public fun AcceptGithubJsonInterceptor() : Interceptor =
    Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Accept", "application/vnd.github+json"
            )
            .build()
        chain.proceed(newRequest)
    }
