package uk.co.jordanterry.otel.services.moshi

import com.squareup.moshi.Moshi
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
public abstract class MoshiComponent {
    @Provides
    public fun provideMoshi(): Moshi =
        Moshi.Builder().build()
}

@Suppress("FunctionName")
fun MoshiGraph(): MoshiComponent =
    MoshiComponent::class.create()