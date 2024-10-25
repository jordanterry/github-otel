package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ReferencedWorkflow(
    @SerialName("path")
    val path: String,
    @SerialName("sha")
    val sha: String,
    @SerialName("ref")
    val ref: String
)
