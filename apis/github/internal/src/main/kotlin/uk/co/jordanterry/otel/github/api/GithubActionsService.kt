package uk.co.jordanterry.otel.github.api

import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.jordanterry.otel.github.api.dtos.WorkflowRun
import uk.co.jordanterry.otel.github.models.OwnerRepo
import uk.co.jordanterry.otel.github.models.Run

internal interface GithubActionsService {

    @GET("repos/{ownerRepo}/actions/runs/{run}")
    suspend fun run(
        @Path("ownerRepo", encoded = true) ownerRepo: OwnerRepo,
        @Path("run") run: Run
    ): WorkflowRun

    @GET("repos/{ownerRepo}/actions/runs")
    suspend fun runsForRepository(
        @Path("ownerRepo", encoded = true) ownerRepo: OwnerRepo,
    ): WorkflowRun
}
