package uk.co.jordanterry.otel.github.api

import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.jordanterry.otel.github.api.dtos.WorkflowRunDto

public interface GithubActionsService {

    @GET("repos/{ownerRepo}/actions/runs/{run}")
    public suspend fun run(
        @Path("ownerRepo", encoded = true) ownerRepo: OwnerRepo,
        @Path("run") run: Run
    ): WorkflowRunDto

    @GET("repos/{ownerRepo}/actions/runs")
    public suspend fun runsForRepository(
        @Path("ownerRepo", encoded = true) ownerRepo: OwnerRepo,
    ): WorkflowRunDto
}
