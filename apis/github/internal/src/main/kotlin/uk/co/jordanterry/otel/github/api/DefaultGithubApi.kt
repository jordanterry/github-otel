package uk.co.jordanterry.otel.github.api

public class DefaultGithubApi(
    private val githubActionsService: GithubActionsService,
) : GithubApi {
    public override suspend fun run(
        ownerRepo: OwnerRepo,
        run: Run
    ): WorkflowRun {
        val workflowRun = githubActionsService.run(ownerRepo, run)
        return WorkflowRun(workflowRun.id)
    }
}