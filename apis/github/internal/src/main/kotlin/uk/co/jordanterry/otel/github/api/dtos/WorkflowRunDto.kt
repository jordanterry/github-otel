package uk.co.jordanterry.otel.github.api.dtos

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class WorkflowRunDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String?,
    @SerialName("head_branch")
    val headBranch: String?,
    @SerialName("path")
    val path: String,
    @SerialName("run_number")
    val runNumber: Int,
    @SerialName("run_attempt")
    val runAttempt: Int,
    @SerialName("referenced_workflows")
    val referencedWorkflows: List<ReferencedWorkflow>?,
    @SerialName("event")
    val event: String,
    @SerialName("status")
    val status: String?,
    @SerialName("conclusion")
    val conclusion: String?,
    @SerialName("workflow_id")
    val workflowId: Int,
    @SerialName("url")
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("pull_requests")
    val pullRequests: List<PullRequest>?,
    @SerialName("created_at")
    @Serializable(with = InstantIso8601Serializer::class)
    val createdAt: Instant,
    @SerialName("updated_at")
    @Serializable(with = InstantIso8601Serializer::class)
    val updatedAt: Instant,
    @SerialName("actor")
    val actor: User,
    @SerialName("triggering_actor")
    val triggeringActor: User,
    @SerialName("run_started_at")
    @Serializable(with = InstantIso8601Serializer::class)
    val runStartedAt: Instant,
    @SerialName("jobs_url")
    val jobsUrl: String,
    @SerialName("logs_url")
    val logsUrl: String,
    @SerialName("check_suite_url")
    val checkSuiteUrl: String,
    @SerialName("artifacts_url")
    val artifactsUrl: String,
    @SerialName("cancel_url")
    val cancelUrl: String,
    @SerialName("rerun_url")
    val rerunUrl: String,
    @SerialName("previous_attempt_url")
    val previousAttemptUrl: String?,
    @SerialName("workflow_url")
    val workflowUrl: String,
    @SerialName("head_commit")
    val headCommit: HeadCommit?,
    @SerialName("repository")
    val repository: Repository
)
