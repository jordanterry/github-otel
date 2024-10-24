package uk.co.jordanterry.otel.github.api.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class ReferencedWorkflow(
    val path: String,
    val sha: String,
    val ref: String
)
