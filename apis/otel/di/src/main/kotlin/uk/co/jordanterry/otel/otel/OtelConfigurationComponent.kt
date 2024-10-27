package uk.co.jordanterry.otel.otel

import io.opentelemetry.sdk.OpenTelemetrySdk
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
public abstract class OtelConfigurationComponent(
    @get:Provides public val otelEndpoint: String,
) {
    @Provides
    public fun provideOtelConfiguration(
        otelEndpoint: String,
    ): OpenTelemetrySdk =
        OtelConfiguration.initOpenTelemetry(otelEndpoint)
}