package uk.co.jordanterry.otel.services.okhttp

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Qualifier
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Qualifier
@Target(
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.TYPE
)
annotation class Named(val value: String)

@Component
public abstract class OkHttpComponent(
    @get:Named("additionalInterceptors") @get:Provides public val additionalInterceptors: Set<Interceptor>,
) {

    public abstract val interceptors: Set<Interceptor>


    @IntoSet
    @Provides
    internal fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    public fun provideOkHttpClient(
        interceptors: Set<Interceptor>,
        @Named("additionalInterceptors") additionalInterceptors: Set<Interceptor>,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            interceptors.forEach { addInterceptor(it) }
            additionalInterceptors.forEach { addInterceptor(it) }
        }.build()
}

@Suppress("FunctionName")
fun OkHttpGraph(
    interceptors: Set<Interceptor> = emptySet(),
): OkHttpComponent =
    OkHttpComponent::class.create(
        interceptors
    )