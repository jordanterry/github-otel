package uk.co.jordanterry.otel.otel

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.context.Context
import kotlinx.datetime.toJavaInstant
import uk.co.jordanterry.otel.github.api.WorkflowRun

public class DefaultOtelReporter(
    private val openTelemetry: OpenTelemetry,
) : OtelReporter {

    public override fun report(run: WorkflowRun) {

        val tracer = openTelemetry.getTracer("uk.co.jordanterry.testing")
        val span = tracer.spanBuilder(run.name)
            .setStartTimestamp(run.startedAt.toJavaInstant())
            .setAttribute("github.type", "workflow")
            .startSpan()
        run.jobs.forEach { job ->
            val jobSpan = tracer
                .spanBuilder(job.name)
                .setParent(span.storeInContext(Context.current()))
                .setStartTimestamp(job.startedAt.toJavaInstant())
                .setAttribute("github.type", "job")
                .startSpan().apply {

                }
            job.steps.forEach { step ->
                tracer
                    .spanBuilder(step.name)
                    .setParent(jobSpan.storeInContext(Context.current()))
                    .setStartTimestamp(step.startedAt!!.toJavaInstant())
                    .setAttribute("github.type", "step")
                    .startSpan().apply {
                        end(step.endedAt!!.toJavaInstant())
                    }
            }
            jobSpan.end(job.endedAt.toJavaInstant())
        }

        span.end(run.endedAt.toJavaInstant())
    }
}