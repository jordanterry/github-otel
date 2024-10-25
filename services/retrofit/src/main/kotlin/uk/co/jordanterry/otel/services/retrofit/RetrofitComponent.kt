package uk.co.jordanterry.otel.services.retrofit

import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import uk.co.jordanterry.otel.services.moshi.JsonSerializationComponent
import uk.co.jordanterry.otel.services.moshi.JsonSerializationGraph
import uk.co.jordanterry.otel.services.okhttp.OkHttpComponent
import uk.co.jordanterry.otel.services.okhttp.OkHttpGraph

@Component
public abstract class RetrofitComponent(
    @Component public val jsonSerializationComponent: JsonSerializationComponent,
    @Component public val okHttpComponent: OkHttpComponent,
    @get:Provides public val retrofitUrl: HttpUrl,
) {

    @Provides
    public fun retrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        retrofitBaseUrl: HttpUrl,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(retrofitBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()

}

@Suppress("FunctionName")
public fun RetrofitGraph(
    jsonComponent: JsonSerializationComponent = JsonSerializationGraph(),
    okHttpComponent: OkHttpComponent = OkHttpGraph(),
    url: HttpUrl,
): RetrofitComponent =
    RetrofitComponent::class.create(
        jsonComponent, okHttpComponent, url
    )