package uk.co.jordanterry.otel.github.api

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.junit5.internal.MockWebServerExtension
import okhttp3.ExperimentalOkHttpApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.co.jordanterry.otel.github.api.di.GithubApiComponent
import uk.co.jordanterry.otel.github.api.di.MoshiComponent
import uk.co.jordanterry.otel.github.api.di.OkHttpComponent
import uk.co.jordanterry.otel.github.api.di.RetrofitComponent
import uk.co.jordanterry.otel.github.api.di.create
import uk.co.jordanterry.otel.github.api.dtos.User
import uk.co.jordanterry.otel.github.models.Owner
import uk.co.jordanterry.otel.github.models.Repo
import uk.co.jordanterry.otel.github.models.Run
import uk.co.jordanterry.otel.github.models.div
import kotlin.math.log

@OptIn(ExperimentalOkHttpApi::class)
@ExtendWith(MockWebServerExtension::class)
class GithubServiceTests(
    private val mockWebServer: MockWebServer,
) {
    private val component = GithubApiComponent::class.create(
        RetrofitComponent::class.create(
            MoshiComponent::class.create(),
            OkHttpComponent::class.create(),
            mockWebServer.url("/").toString(),
        )
    )

    private val subject: GithubApi = component.githubApi

    @Test
    fun `runs request and parsing`() = runTest {
        mockWebServer.enqueue(
            MockResponse().newBuilder()
                .body(RUNS_RESPONSE)
                .build()
        )
        val run = subject.run(
            ownerRepo = Owner("jordanterry") / Repo("blog"),
            run = Run(11425826679)
        )

        assertSoftly {
            run.id shouldBe 11425826679
            run.run_attempt shouldBe 1
            run.actor shouldBe User(
                login = "jordanterry",
            )
            run.jobs_url shouldBe "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679/jobs"
        }
    }
}

private val RUNS_RESPONSE: String = """
    {
      "id": 11425826679,
      "name": "Deploy Jekyll site to Pages",
      "node_id": "WFR_kwLOIvdlM88AAAACqQhHdw",
      "head_branch": "main",
      "head_sha": "48cedfb9b46556d0b67ce84a9eeed3ad9a628d8d",
      "path": ".github/workflows/jekyll.yml",
      "display_title": "Add kotlin specification to code block",
      "run_number": 29,
      "event": "push",
      "status": "completed",
      "conclusion": "success",
      "workflow_id": 44937616,
      "check_suite_id": 29846982889,
      "check_suite_node_id": "CS_kwDOIvdlM88AAAAG8wTQ6Q",
      "url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679",
      "html_url": "https://github.com/jordanterry/blog/actions/runs/11425826679",
      "pull_requests": [

      ],
      "created_at": "2024-10-20T11:11:36Z",
      "updated_at": "2024-10-20T11:12:13Z",
      "actor": {
        "login": "jordanterry",
        "id": 1228761,
        "node_id": "MDQ6VXNlcjEyMjg3NjE=",
        "avatar_url": "https://avatars.githubusercontent.com/u/1228761?v=4",
        "gravatar_id": "",
        "url": "https://api.github.com/users/jordanterry",
        "html_url": "https://github.com/jordanterry",
        "followers_url": "https://api.github.com/users/jordanterry/followers",
        "following_url": "https://api.github.com/users/jordanterry/following{/other_user}",
        "gists_url": "https://api.github.com/users/jordanterry/gists{/gist_id}",
        "starred_url": "https://api.github.com/users/jordanterry/starred{/owner}{/repo}",
        "subscriptions_url": "https://api.github.com/users/jordanterry/subscriptions",
        "organizations_url": "https://api.github.com/users/jordanterry/orgs",
        "repos_url": "https://api.github.com/users/jordanterry/repos",
        "events_url": "https://api.github.com/users/jordanterry/events{/privacy}",
        "received_events_url": "https://api.github.com/users/jordanterry/received_events",
        "type": "User",
        "user_view_type": "public",
        "site_admin": false
      },
      "run_attempt": 1,
      "referenced_workflows": [

      ],
      "run_started_at": "2024-10-20T11:11:36Z",
      "triggering_actor": {
        "login": "jordanterry",
        "id": 1228761,
        "node_id": "MDQ6VXNlcjEyMjg3NjE=",
        "avatar_url": "https://avatars.githubusercontent.com/u/1228761?v=4",
        "gravatar_id": "",
        "url": "https://api.github.com/users/jordanterry",
        "html_url": "https://github.com/jordanterry",
        "followers_url": "https://api.github.com/users/jordanterry/followers",
        "following_url": "https://api.github.com/users/jordanterry/following{/other_user}",
        "gists_url": "https://api.github.com/users/jordanterry/gists{/gist_id}",
        "starred_url": "https://api.github.com/users/jordanterry/starred{/owner}{/repo}",
        "subscriptions_url": "https://api.github.com/users/jordanterry/subscriptions",
        "organizations_url": "https://api.github.com/users/jordanterry/orgs",
        "repos_url": "https://api.github.com/users/jordanterry/repos",
        "events_url": "https://api.github.com/users/jordanterry/events{/privacy}",
        "received_events_url": "https://api.github.com/users/jordanterry/received_events",
        "type": "User",
        "user_view_type": "public",
        "site_admin": false
      },
      "jobs_url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679/jobs",
      "logs_url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679/logs",
      "check_suite_url": "https://api.github.com/repos/jordanterry/blog/check-suites/29846982889",
      "artifacts_url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679/artifacts",
      "cancel_url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679/cancel",
      "rerun_url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679/rerun",
      "previous_attempt_url": null,
      "workflow_url": "https://api.github.com/repos/jordanterry/blog/actions/workflows/44937616",
      "head_commit": {
        "id": "48cedfb9b46556d0b67ce84a9eeed3ad9a628d8d",
        "tree_id": "83ee6a751c0fad0fc562e9756c6a20d0fe30a967",
        "message": "Add kotlin specification to code block",
        "timestamp": "2024-10-20T11:11:29Z",
        "author": {
          "name": "Jordan Terry",
          "email": "jordanfterry@gmail.com"
        },
        "committer": {
          "name": "Jordan Terry",
          "email": "jordanfterry@gmail.com"
        }
      },
      "repository": {
        "id": 586638643,
        "node_id": "R_kgDOIvdlMw",
        "name": "blog",
        "full_name": "jordanterry/blog",
        "private": false,
        "owner": {
          "login": "jordanterry",
          "id": 1228761,
          "node_id": "MDQ6VXNlcjEyMjg3NjE=",
          "avatar_url": "https://avatars.githubusercontent.com/u/1228761?v=4",
          "gravatar_id": "",
          "url": "https://api.github.com/users/jordanterry",
          "html_url": "https://github.com/jordanterry",
          "followers_url": "https://api.github.com/users/jordanterry/followers",
          "following_url": "https://api.github.com/users/jordanterry/following{/other_user}",
          "gists_url": "https://api.github.com/users/jordanterry/gists{/gist_id}",
          "starred_url": "https://api.github.com/users/jordanterry/starred{/owner}{/repo}",
          "subscriptions_url": "https://api.github.com/users/jordanterry/subscriptions",
          "organizations_url": "https://api.github.com/users/jordanterry/orgs",
          "repos_url": "https://api.github.com/users/jordanterry/repos",
          "events_url": "https://api.github.com/users/jordanterry/events{/privacy}",
          "received_events_url": "https://api.github.com/users/jordanterry/received_events",
          "type": "User",
          "user_view_type": "public",
          "site_admin": false
        },
        "html_url": "https://github.com/jordanterry/blog",
        "description": null,
        "fork": false,
        "url": "https://api.github.com/repos/jordanterry/blog",
        "forks_url": "https://api.github.com/repos/jordanterry/blog/forks",
        "keys_url": "https://api.github.com/repos/jordanterry/blog/keys{/key_id}",
        "collaborators_url": "https://api.github.com/repos/jordanterry/blog/collaborators{/collaborator}",
        "teams_url": "https://api.github.com/repos/jordanterry/blog/teams",
        "hooks_url": "https://api.github.com/repos/jordanterry/blog/hooks",
        "issue_events_url": "https://api.github.com/repos/jordanterry/blog/issues/events{/number}",
        "events_url": "https://api.github.com/repos/jordanterry/blog/events",
        "assignees_url": "https://api.github.com/repos/jordanterry/blog/assignees{/user}",
        "branches_url": "https://api.github.com/repos/jordanterry/blog/branches{/branch}",
        "tags_url": "https://api.github.com/repos/jordanterry/blog/tags",
        "blobs_url": "https://api.github.com/repos/jordanterry/blog/git/blobs{/sha}",
        "git_tags_url": "https://api.github.com/repos/jordanterry/blog/git/tags{/sha}",
        "git_refs_url": "https://api.github.com/repos/jordanterry/blog/git/refs{/sha}",
        "trees_url": "https://api.github.com/repos/jordanterry/blog/git/trees{/sha}",
        "statuses_url": "https://api.github.com/repos/jordanterry/blog/statuses/{sha}",
        "languages_url": "https://api.github.com/repos/jordanterry/blog/languages",
        "stargazers_url": "https://api.github.com/repos/jordanterry/blog/stargazers",
        "contributors_url": "https://api.github.com/repos/jordanterry/blog/contributors",
        "subscribers_url": "https://api.github.com/repos/jordanterry/blog/subscribers",
        "subscription_url": "https://api.github.com/repos/jordanterry/blog/subscription",
        "commits_url": "https://api.github.com/repos/jordanterry/blog/commits{/sha}",
        "git_commits_url": "https://api.github.com/repos/jordanterry/blog/git/commits{/sha}",
        "comments_url": "https://api.github.com/repos/jordanterry/blog/comments{/number}",
        "issue_comment_url": "https://api.github.com/repos/jordanterry/blog/issues/comments{/number}",
        "contents_url": "https://api.github.com/repos/jordanterry/blog/contents/{+path}",
        "compare_url": "https://api.github.com/repos/jordanterry/blog/compare/{base}...{head}",
        "merges_url": "https://api.github.com/repos/jordanterry/blog/merges",
        "archive_url": "https://api.github.com/repos/jordanterry/blog/{archive_format}{/ref}",
        "downloads_url": "https://api.github.com/repos/jordanterry/blog/downloads",
        "issues_url": "https://api.github.com/repos/jordanterry/blog/issues{/number}",
        "pulls_url": "https://api.github.com/repos/jordanterry/blog/pulls{/number}",
        "milestones_url": "https://api.github.com/repos/jordanterry/blog/milestones{/number}",
        "notifications_url": "https://api.github.com/repos/jordanterry/blog/notifications{?since,all,participating}",
        "labels_url": "https://api.github.com/repos/jordanterry/blog/labels{/name}",
        "releases_url": "https://api.github.com/repos/jordanterry/blog/releases{/id}",
        "deployments_url": "https://api.github.com/repos/jordanterry/blog/deployments"
      },
      "head_repository": {
        "id": 586638643,
        "node_id": "R_kgDOIvdlMw",
        "name": "blog",
        "full_name": "jordanterry/blog",
        "private": false,
        "owner": {
          "login": "jordanterry",
          "id": 1228761,
          "node_id": "MDQ6VXNlcjEyMjg3NjE=",
          "avatar_url": "https://avatars.githubusercontent.com/u/1228761?v=4",
          "gravatar_id": "",
          "url": "https://api.github.com/users/jordanterry",
          "html_url": "https://github.com/jordanterry",
          "followers_url": "https://api.github.com/users/jordanterry/followers",
          "following_url": "https://api.github.com/users/jordanterry/following{/other_user}",
          "gists_url": "https://api.github.com/users/jordanterry/gists{/gist_id}",
          "starred_url": "https://api.github.com/users/jordanterry/starred{/owner}{/repo}",
          "subscriptions_url": "https://api.github.com/users/jordanterry/subscriptions",
          "organizations_url": "https://api.github.com/users/jordanterry/orgs",
          "repos_url": "https://api.github.com/users/jordanterry/repos",
          "events_url": "https://api.github.com/users/jordanterry/events{/privacy}",
          "received_events_url": "https://api.github.com/users/jordanterry/received_events",
          "type": "User",
          "user_view_type": "public",
          "site_admin": false
        },
        "html_url": "https://github.com/jordanterry/blog",
        "description": null,
        "fork": false,
        "url": "https://api.github.com/repos/jordanterry/blog",
        "forks_url": "https://api.github.com/repos/jordanterry/blog/forks",
        "keys_url": "https://api.github.com/repos/jordanterry/blog/keys{/key_id}",
        "collaborators_url": "https://api.github.com/repos/jordanterry/blog/collaborators{/collaborator}",
        "teams_url": "https://api.github.com/repos/jordanterry/blog/teams",
        "hooks_url": "https://api.github.com/repos/jordanterry/blog/hooks",
        "issue_events_url": "https://api.github.com/repos/jordanterry/blog/issues/events{/number}",
        "events_url": "https://api.github.com/repos/jordanterry/blog/events",
        "assignees_url": "https://api.github.com/repos/jordanterry/blog/assignees{/user}",
        "branches_url": "https://api.github.com/repos/jordanterry/blog/branches{/branch}",
        "tags_url": "https://api.github.com/repos/jordanterry/blog/tags",
        "blobs_url": "https://api.github.com/repos/jordanterry/blog/git/blobs{/sha}",
        "git_tags_url": "https://api.github.com/repos/jordanterry/blog/git/tags{/sha}",
        "git_refs_url": "https://api.github.com/repos/jordanterry/blog/git/refs{/sha}",
        "trees_url": "https://api.github.com/repos/jordanterry/blog/git/trees{/sha}",
        "statuses_url": "https://api.github.com/repos/jordanterry/blog/statuses/{sha}",
        "languages_url": "https://api.github.com/repos/jordanterry/blog/languages",
        "stargazers_url": "https://api.github.com/repos/jordanterry/blog/stargazers",
        "contributors_url": "https://api.github.com/repos/jordanterry/blog/contributors",
        "subscribers_url": "https://api.github.com/repos/jordanterry/blog/subscribers",
        "subscription_url": "https://api.github.com/repos/jordanterry/blog/subscription",
        "commits_url": "https://api.github.com/repos/jordanterry/blog/commits{/sha}",
        "git_commits_url": "https://api.github.com/repos/jordanterry/blog/git/commits{/sha}",
        "comments_url": "https://api.github.com/repos/jordanterry/blog/comments{/number}",
        "issue_comment_url": "https://api.github.com/repos/jordanterry/blog/issues/comments{/number}",
        "contents_url": "https://api.github.com/repos/jordanterry/blog/contents/{+path}",
        "compare_url": "https://api.github.com/repos/jordanterry/blog/compare/{base}...{head}",
        "merges_url": "https://api.github.com/repos/jordanterry/blog/merges",
        "archive_url": "https://api.github.com/repos/jordanterry/blog/{archive_format}{/ref}",
        "downloads_url": "https://api.github.com/repos/jordanterry/blog/downloads",
        "issues_url": "https://api.github.com/repos/jordanterry/blog/issues{/number}",
        "pulls_url": "https://api.github.com/repos/jordanterry/blog/pulls{/number}",
        "milestones_url": "https://api.github.com/repos/jordanterry/blog/milestones{/number}",
        "notifications_url": "https://api.github.com/repos/jordanterry/blog/notifications{?since,all,participating}",
        "labels_url": "https://api.github.com/repos/jordanterry/blog/labels{/name}",
        "releases_url": "https://api.github.com/repos/jordanterry/blog/releases{/id}",
        "deployments_url": "https://api.github.com/repos/jordanterry/blog/deployments"
      }
    }
""".trimIndent()