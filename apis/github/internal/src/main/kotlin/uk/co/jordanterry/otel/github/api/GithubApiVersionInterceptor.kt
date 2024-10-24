package uk.co.jordanterry.otel.github.api

import okhttp3.Interceptor

@Suppress("FunctionName")
public fun GithubApiVersionInterceptor() : Interceptor =
    Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "X-GitHub-Api-Version", "2022-11-28"
            )
            .build()
        chain.proceed(newRequest)
    }