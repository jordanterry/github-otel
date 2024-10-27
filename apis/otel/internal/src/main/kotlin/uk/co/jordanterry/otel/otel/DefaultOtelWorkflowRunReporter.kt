package uk.co.jordanterry.otel.otel

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanBuilder
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import uk.co.jordanterry.otel.github.api.Job
import uk.co.jordanterry.otel.github.api.Repo
import uk.co.jordanterry.otel.github.api.RunStatus
import uk.co.jordanterry.otel.github.api.Step
import uk.co.jordanterry.otel.github.api.WorkflowRun

public class DefaultOtelWorkflowRunReporter(
    private val openTelemetry: OpenTelemetry,
) : OtelWorkflowRunReporter {

    public override fun report(run: WorkflowRun) {
        val tracer = openTelemetry.getTracer("uk.co.jordanterry.testing")
        val span = tracer
            .spanBuilder(run)
            .startSpan()
            .setStatus(run.status.toStatusCode())

        run.jobs.forEach { job ->
            val jobSpan = tracer
                .spanBuilder(job)
                .setParent(span.storeInContext(Context.current()))
                .startSpan()
                .setStatus(job.status.toStatusCode())

            job.steps.forEach { step ->
                tracer
                    .spanBuilder(step)
                    .setParent(jobSpan.storeInContext(Context.current()))
                    .startSpan()
                    .setStatus(step.status.toStatusCode())
                    .apply {
                        val endedAt = step.endedAt
                        if (endedAt != null) {
                            end(endedAt)
                        }
                    }
            }
            jobSpan.end(job.endedAt)
        }
        span.end(run.endedAt)
    }
}

internal fun SpanBuilder.setAttribute(repo: Repo): SpanBuilder =
    setAttribute("github.repo", repo.value)

internal fun Tracer.spanBuilder(workflowRun: WorkflowRun): SpanBuilder =
    spanBuilder(workflowRun.name)
        .setAttribute("github.type", "workflow")
        .setAttribute(workflowRun.repo)
        .setStartTimestamp(workflowRun.startedAt)

internal fun Tracer.spanBuilder(job: Job): SpanBuilder =
    spanBuilder(job.name)
        .setAttribute("github.type", "job")
        .setStartTimestamp(job.startedAt)

internal fun Tracer.spanBuilder(step: Step): SpanBuilder =
    spanBuilder(step.name)
        .setAttribute("github.type", "step").apply {
            val startedAt = step.startedAt
            if (startedAt != null) {
                setStartTimestamp(startedAt)
            }
        }.apply {
        }

internal fun SpanBuilder.setStartTimestamp(instant: Instant): SpanBuilder =
    setStartTimestamp(instant.toJavaInstant())

internal fun Span.end(instant: Instant) {
    end(instant.toJavaInstant())
}

private fun RunStatus.toStatusCode(): StatusCode =
    when (this) {
        RunStatus.Success -> StatusCode.OK
        RunStatus.Failure -> StatusCode.ERROR
        RunStatus.Unknown -> StatusCode.UNSET
        RunStatus.Skipped -> StatusCode.UNSET
        RunStatus.Cancelled -> StatusCode.UNSET
    }