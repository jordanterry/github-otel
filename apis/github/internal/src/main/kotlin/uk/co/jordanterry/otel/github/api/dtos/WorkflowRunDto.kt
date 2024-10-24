package uk.co.jordanterry.otel.github.api.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class WorkflowRunDto(
    val id: Long,
    val name: String?,
    val head_branch: String?,
    val path: String,
    val run_number: Int,
    val run_attempt: Int,
    val referenced_workflows: List<ReferencedWorkflow>?,
    val event: String,
    val status: String?,
    val conclusion: String?,
    val workflow_id: Int,
    val url: String,
    val html_url: String,
    val pull_requests: List<PullRequest>?,
    val created_at: String, // Instant or OffsetDateTime depending on your needs
    val updated_at: String, // Instant or OffsetDateTime depending on your needs
    val actor: User,
    val triggering_actor: User,
    val run_started_at: String, // Instant or OffsetDateTime depending on your needs
    val jobs_url: String,
    val logs_url: String,
    val check_suite_url: String,
    val artifacts_url: String,
    val cancel_url: String,
    val rerun_url: String,
    val previous_attempt_url: String?,
    val workflow_url: String,
    val head_commit: HeadCommit?,
    val repository: Repository
)
