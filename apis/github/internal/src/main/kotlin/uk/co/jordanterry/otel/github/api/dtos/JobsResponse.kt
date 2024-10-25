package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class JobsResponse(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("jobs")
    val jobs: List<GithubJob>,
)
