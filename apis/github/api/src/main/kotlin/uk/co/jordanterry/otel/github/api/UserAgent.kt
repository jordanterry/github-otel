package uk.co.jordanterry.otel.github.api

import kotlinx.datetime.Instant

@JvmInline
public value class Owner(public val value: String)

@JvmInline
public value class Repo(public val value: String)

@JvmInline
public value class OwnerRepo internal constructor(public val value: String)

public operator fun Owner.div(repo: Repo): OwnerRepo =
    OwnerRepo(value + "/" + repo.value)

@JvmInline
public value class Run(public val value: Long)

public data class WorkflowRun(
    val id: Long,
    val name: String,
    val startedAt: Instant,
    val endedAt: Instant,
    val jobs: List<Job>,
)

public data class Job(
    val name: String,
    val startedAt: Instant,
    val endedAt: Instant,
    val steps: List<Step>,
)

public data class Step(
    val name: String,
    val startedAt: Instant? = null,
    val endedAt: Instant? = null,
)