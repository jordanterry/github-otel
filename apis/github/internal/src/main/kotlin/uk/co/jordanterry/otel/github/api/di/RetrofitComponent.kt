package uk.co.jordanterry.otel.github.api.di

import com.squareup.moshi.Moshi
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal typealias RetrofitBaseUrl = String

@Component
public abstract class RetrofitComponent(
    @Component public val moshiComponent: MoshiComponent,
    @Component public val okHttpComponent: OkHttpComponent,
    private val retrofitUrl: RetrofitBaseUrl,
) {

    @Provides
    public fun provideRetrofitBaseUrl(): RetrofitBaseUrl = retrofitUrl

    @Provides
    internal fun retrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        retrofitBaseUrl: RetrofitBaseUrl,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(retrofitBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

}