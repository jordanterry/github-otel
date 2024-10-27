package uk.co.jordanterry.otel.otel

import uk.co.jordanterry.otel.github.api.WorkflowRun

public interface OtelReporter {

    public fun report(run: WorkflowRun)
}