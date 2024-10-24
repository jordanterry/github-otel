package uk.co.jordanterry.otel.services.retrofit

import com.squareup.moshi.Moshi
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.jordanterry.otel.services.moshi.MoshiComponent
import uk.co.jordanterry.otel.services.moshi.MoshiGraph
import uk.co.jordanterry.otel.services.okhttp.OkHttpComponent
import uk.co.jordanterry.otel.services.okhttp.OkHttpGraph


internal typealias RetrofitBaseUrl = String

@Component
public abstract class RetrofitComponent(
    @Component public val moshiComponent: MoshiComponent,
    @Component public val okHttpComponent: OkHttpComponent,
    @get:Provides public val retrofitUrl: HttpUrl,
) {

    @Provides
    public fun retrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        retrofitBaseUrl: HttpUrl,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(retrofitBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

}

public fun RetrofitGraph(
    moshiComponent: MoshiComponent = MoshiGraph(),
    okHttpComponent: OkHttpComponent = OkHttpGraph(),
    url: HttpUrl,
): RetrofitComponent =
    RetrofitComponent::class.create(
        moshiComponent, okHttpComponent, url
    )