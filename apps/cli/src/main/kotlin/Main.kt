import kotlinx.coroutines.runBlocking
import uk.co.jordanterry.otel.github.api.GithubApiComponent
import uk.co.jordanterry.otel.github.api.GithubApiGraph
import uk.co.jordanterry.otel.github.api.Owner
import uk.co.jordanterry.otel.github.api.Repo
import uk.co.jordanterry.otel.github.api.Run
import uk.co.jordanterry.otel.github.api.div
import uk.co.jordanterry.otel.otel.OtelComponent
import uk.co.jordanterry.otel.otel.OtelGraph

fun main() {
    val githubComponent: GithubApiComponent = GithubApiGraph()
    val otelComponent: OtelComponent = OtelGraph(
        otelEndpoint = "http://localhost:4317",
    )
    runBlocking {
        otelComponent.otelWorkflowRunReporter.report(
            githubComponent.githubApi.run(
                Owner("Square") / Repo("okhttp"),
                Run(11535278483)
            )
        )

        otelComponent.otelWorkflowRunReporter.report(
            githubComponent.githubApi.run(
                Owner("Square") / Repo("okhttp"),
                Run(11419785815)
            )
        )
    }
}