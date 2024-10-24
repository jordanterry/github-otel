package uk.co.jordanterry.otel.github.api

public interface GithubApi {
    public suspend fun run(
        ownerRepo: OwnerRepo,
        run: Run
    ): WorkflowRun
}