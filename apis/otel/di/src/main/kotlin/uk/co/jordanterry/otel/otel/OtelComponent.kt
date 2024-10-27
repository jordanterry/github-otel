package uk.co.jordanterry.otel.otel

import io.opentelemetry.sdk.OpenTelemetrySdk
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import uk.co.jordanterry.otel.github.api.Repo
import uk.co.jordanterry.otel.github.api.RunStatus
import uk.co.jordanterry.otel.github.api.WorkflowRun
import kotlin.time.Duration.Companion.minutes

@Component
public abstract class OtelComponent(
    @Component public val otelConfigurationComponent: OtelConfigurationComponent,
) {

    @Provides
    public fun provideOtelReporter(
        openTelemetry: OpenTelemetrySdk,
    ): OtelWorkflowRunReporter =
        DefaultOtelWorkflowRunReporter(openTelemetry)

    public abstract val otelWorkflowRunReporter: OtelWorkflowRunReporter
}

@Suppress("FunctionName")
public fun OtelGraph(
    otelEndpoint: String,
): OtelComponent {
    val otelConfigurationComponent = OtelConfigurationComponent::class.create(otelEndpoint)
    return OtelComponent::class.create(
        otelConfigurationComponent,
    )
}

public fun main() {
    val otelConfigurationComponent = OtelConfigurationComponent::class.create("http://localhost:4317")
    val otelComponent = OtelComponent::class.create(
        otelConfigurationComponent
    )

    val otelReporter = otelComponent.otelWorkflowRunReporter

    otelReporter.report(
        WorkflowRun(
            id = 123130130L,
            name = "here is a test",
            startedAt = Clock.System.now(),
            endedAt = Clock.System.now() + 5.minutes,
            jobs = emptyList(),
            repo = Repo("jordanterry/blog"),
            status = RunStatus.Success,
        )
    )

    println(otelComponent.otelWorkflowRunReporter)
}