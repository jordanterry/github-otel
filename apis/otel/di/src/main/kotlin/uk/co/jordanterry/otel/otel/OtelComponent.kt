package uk.co.jordanterry.otel.otel

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.sdk.OpenTelemetrySdk
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import uk.co.jordanterry.otel.github.api.WorkflowRun
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.minutes

@Component
public abstract class OtelComponent(
    @Component public val otelConfigurationComponent: OtelConfigurationComponent,
) {

    @Provides
    public fun provideOtelReporter(
        openTelemetry: OpenTelemetrySdk,
    ): OtelReporter =
        DefaultOtelReporter(openTelemetry)

    public abstract val otelReporter: OtelReporter
}

public fun main() {
    val otelConfigurationComponent = OtelConfigurationComponent::class.create("http://localhost:4317")
    val otelComponent = OtelComponent::class.create(
        otelConfigurationComponent
    )

    val otelReporter = otelComponent.otelReporter

    otelReporter.report(
        WorkflowRun(
            id = 123130130L,
            name = "here is a test",
            startedAt = Clock.System.now(),
            endedAt = Clock.System.now() + 5.minutes,
            jobs = emptyList()
        )
    )

    println(otelComponent.otelReporter)
}