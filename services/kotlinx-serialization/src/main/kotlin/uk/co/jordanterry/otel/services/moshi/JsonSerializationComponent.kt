package uk.co.jordanterry.otel.services.moshi

import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
public abstract class JsonSerializationComponent {
    @Provides
    public fun provideJson(): Json =
        Json { ignoreUnknownKeys = true}
}

@Suppress("FunctionName")
fun JsonSerializationGraph(): JsonSerializationComponent =
    JsonSerializationComponent::class.create()