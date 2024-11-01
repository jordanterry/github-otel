package uk.co.jordanterry.otel.github.api

import uk.co.jordanterry.otel.github.api.dtos.GithubJob
import uk.co.jordanterry.otel.github.api.dtos.GithubStep

public class DefaultGithubApi(
    private val githubActionsService: GithubActionsService,
) : GithubApi {
    public override suspend fun run(
        ownerRepo: OwnerRepo,
        run: Run
    ): WorkflowRun {
        val workflowRun = githubActionsService
            .run(ownerRepo, run)

        val githubJobsResponse = githubActionsService
            .jobsForWorkflowRun(ownerRepo, run)

        return WorkflowRun(
            id = workflowRun.id,
            name = workflowRun.name!!,
            startedAt = workflowRun.runStartedAt,
            endedAt = workflowRun.updatedAt,
            jobs = githubJobsResponse.jobs.map(::Job),
            repo = workflowRun.repository.name.let(::Repo),
            status = workflowRun.conclusion.toRunStatus(),
        )
    }
}

private fun Step(githubStep: GithubStep): Step =
    Step(
        name = githubStep.name,
        startedAt = githubStep.startedAt,
        endedAt = githubStep.completedAt,
        status = githubStep.conclusion.toRunStatus(),
    )

private fun Job(githubJob: GithubJob): Job =
    Job(
        name = githubJob.name,
        startedAt = githubJob.createdAt,
        endedAt = githubJob.completedAt,
        steps = githubJob.steps.map(::Step),
        status = githubJob.conclusion.toRunStatus(),
    )

private fun String?.toRunStatus(): RunStatus =
    when (this) {
        "completed" -> RunStatus.Success
        "failure" -> RunStatus.Failure
        "cancelled" -> RunStatus.Cancelled
        "skipped" -> RunStatus.Skipped
        else -> RunStatus.Unknown
    }