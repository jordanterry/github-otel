package uk.co.jordanterry.otel.github.api.di

import com.squareup.moshi.Moshi
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
public abstract class MoshiComponent {
    @Provides
    internal fun provideMoshi(): Moshi =
        Moshi.Builder().build()
}