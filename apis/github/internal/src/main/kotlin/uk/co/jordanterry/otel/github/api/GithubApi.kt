package uk.co.jordanterry.otel.github.api

import me.tatarka.inject.annotations.Inject
import uk.co.jordanterry.otel.github.api.dtos.WorkflowRun
import uk.co.jordanterry.otel.github.models.OwnerRepo
import uk.co.jordanterry.otel.github.models.Run

@Inject
public class GithubApi internal constructor(
    private val githubActionsService: GithubActionsService,
) {
    public suspend fun run(
        ownerRepo: OwnerRepo,
        run: Run
    ): WorkflowRun =
        githubActionsService.run(ownerRepo, run)
}