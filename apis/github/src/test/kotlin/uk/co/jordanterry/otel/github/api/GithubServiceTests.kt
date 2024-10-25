package uk.co.jordanterry.otel.github.api

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.SocketPolicy
import mockwebserver3.junit5.internal.MockWebServerExtension
import okhttp3.ExperimentalOkHttpApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalOkHttpApi::class)
@ExtendWith(MockWebServerExtension::class)
class GithubServiceTests(
    private val mockWebServer: MockWebServer,
) {

    private val githubComponent: GithubApiComponent = GithubApiGraph(
        url = mockWebServer.url("/")
    )
    private val subject: GithubApi = githubComponent.githubApi

    @Test
    fun `runs request and parsing`() = runTest {
        mockWebServer.enqueue(
            MockResponse().newBuilder()
                .body(RUNS_RESPONSE)
                .build()
        )
        mockWebServer.enqueue(
            MockResponse().newBuilder()
                .body(WORKFLOW_RUN_STEPS)
                .build()
        )
        val run = subject.run(
            ownerRepo = Owner("jordanterry") / Repo("blog"),
            run = Run(11425826679)
        )

        val headers = mockWebServer.takeRequest().headers

        assertSoftly {
            headers["Accept"] shouldBe "application/vnd.github+json"
            headers["X-Github-Api-Version"] shouldBe "2022-11-28"
        }

        assertSoftly {
            run.id shouldBe 11425826679
            run.jobs shouldHaveSize 2
            run.jobs[0].steps shouldHaveSize  8
            run.jobs[1].steps shouldHaveSize 3
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


private const val WORKFLOW_RUN_STEPS: String = """
    {
  "total_count": 2,
  "jobs": [
    {
      "id": 31787995325,
      "run_id": 11425826679,
      "workflow_name": "Deploy Jekyll site to Pages",
      "head_branch": "main",
      "run_url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679",
      "run_attempt": 1,
      "node_id": "CR_kwDOIvdlM88AAAAHZrZQvQ",
      "head_sha": "48cedfb9b46556d0b67ce84a9eeed3ad9a628d8d",
      "url": "https://api.github.com/repos/jordanterry/blog/actions/jobs/31787995325",
      "html_url": "https://github.com/jordanterry/blog/actions/runs/11425826679/job/31787995325",
      "status": "completed",
      "conclusion": "success",
      "created_at": "2024-10-20T11:11:37Z",
      "started_at": "2024-10-20T11:11:43Z",
      "completed_at": "2024-10-20T11:11:55Z",
      "name": "build",
      "steps": [
        {
          "name": "Set up job",
          "status": "completed",
          "conclusion": "success",
          "number": 1,
          "started_at": "2024-10-20T11:11:43Z",
          "completed_at": "2024-10-20T11:11:44Z"
        },
        {
          "name": "Checkout",
          "status": "completed",
          "conclusion": "success",
          "number": 2,
          "started_at": "2024-10-20T11:11:44Z",
          "completed_at": "2024-10-20T11:11:46Z"
        },
        {
          "name": "Setup Ruby",
          "status": "completed",
          "conclusion": "success",
          "number": 3,
          "started_at": "2024-10-20T11:11:47Z",
          "completed_at": "2024-10-20T11:11:50Z"
        },
        {
          "name": "Setup Pages",
          "status": "completed",
          "conclusion": "success",
          "number": 4,
          "started_at": "2024-10-20T11:11:50Z",
          "completed_at": "2024-10-20T11:11:50Z"
        },
        {
          "name": "Build with Jekyll",
          "status": "completed",
          "conclusion": "success",
          "number": 5,
          "started_at": "2024-10-20T11:11:50Z",
          "completed_at": "2024-10-20T11:11:52Z"
        },
        {
          "name": "Upload artifact",
          "status": "completed",
          "conclusion": "success",
          "number": 6,
          "started_at": "2024-10-20T11:11:52Z",
          "completed_at": "2024-10-20T11:11:54Z"
        },
        {
          "name": "Post Checkout",
          "status": "completed",
          "conclusion": "success",
          "number": 12,
          "started_at": "2024-10-20T11:11:55Z",
          "completed_at": "2024-10-20T11:11:55Z"
        },
        {
          "name": "Complete job",
          "status": "completed",
          "conclusion": "success",
          "number": 13,
          "started_at": "2024-10-20T11:11:54Z",
          "completed_at": "2024-10-20T11:11:54Z"
        }
      ],
      "check_run_url": "https://api.github.com/repos/jordanterry/blog/check-runs/31787995325",
      "labels": [
        "ubuntu-latest"
      ],
      "runner_id": 20,
      "runner_name": "GitHub Actions 20",
      "runner_group_id": 2,
      "runner_group_name": "GitHub Actions"
    },
    {
      "id": 31787998306,
      "run_id": 11425826679,
      "workflow_name": "Deploy Jekyll site to Pages",
      "head_branch": "main",
      "run_url": "https://api.github.com/repos/jordanterry/blog/actions/runs/11425826679",
      "run_attempt": 1,
      "node_id": "CR_kwDOIvdlM88AAAAHZrZcYg",
      "head_sha": "48cedfb9b46556d0b67ce84a9eeed3ad9a628d8d",
      "url": "https://api.github.com/repos/jordanterry/blog/actions/jobs/31787998306",
      "html_url": "https://github.com/jordanterry/blog/actions/runs/11425826679/job/31787998306",
      "status": "completed",
      "conclusion": "success",
      "created_at": "2024-10-20T11:11:57Z",
      "started_at": "2024-10-20T11:12:05Z",
      "completed_at": "2024-10-20T11:12:12Z",
      "name": "deploy",
      "steps": [
        {
          "name": "Set up job",
          "status": "completed",
          "conclusion": "success",
          "number": 1,
          "started_at": "2024-10-20T11:12:04Z",
          "completed_at": "2024-10-20T11:12:05Z"
        },
        {
          "name": "Deploy to GitHub Pages",
          "status": "completed",
          "conclusion": "success",
          "number": 2,
          "started_at": "2024-10-20T11:12:05Z",
          "completed_at": "2024-10-20T11:12:11Z"
        },
        {
          "name": "Complete job",
          "status": "completed",
          "conclusion": "success",
          "number": 3,
          "started_at": "2024-10-20T11:12:11Z",
          "completed_at": "2024-10-20T11:12:11Z"
        }
      ],
      "check_run_url": "https://api.github.com/repos/jordanterry/blog/check-runs/31787998306",
      "labels": [
        "ubuntu-latest"
      ],
      "runner_id": 17,
      "runner_name": "GitHub Actions 17",
      "runner_group_id": 2,
      "runner_group_name": "GitHub Actions"
    }
  ]
}
"""