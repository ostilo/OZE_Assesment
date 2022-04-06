package com.oze.dev

import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.db.model.DevelopersResult
import com.oze.dev.db.model.FetchDataModel
import com.oze.dev.db.model.repo.DeveloperRepoResult
import com.oze.dev.db.model.repo.DeveloperRepoResultItem

class MockTestUtil {
    companion object {
        fun createZeroUsers(): DevelopersResult {
            return DevelopersResult(
                incompleteResults = false,
                totalCount = 0,
                usersInfo = listOf()
            )
        }

        fun createUsers(): DevelopersResult {
            return DevelopersResult(
                incompleteResults = false,
                totalCount = 1,
                usersInfo = createUsersResultsList()
            )
        }

        fun createRepos(): DeveloperRepoResult {
            return DeveloperRepoResult().apply {
                this.addAll(createUserReposList())
            }
        }


        fun createSingleUser():DeveloperData{
            return DeveloperData(
                login= "ostilo",
                id= 72912588,
                nodeId= "MDQ6VXNlcjcyOTEyNTg4",
                avatarUrl= "https://avatars.githubusercontent.com/u/72912588?v=4",
                gravatarId= "",
                url= "https://api.github.com/users/alimohamadi17",
                htmlUrl= "https://github.com/alimohamadi17",
                followersUrl= "https://api.github.com/users/alimohamadi17/followers",
                followingUrl= "https://api.github.com/users/alimohamadi17/following{/other_user}",
                gistsUrl= "https://api.github.com/users/alimohamadi17/gists{/gist_id}",
                starredUrl= "https://api.github.com/users/alimohamadi17/starred{/owner}{/repo}",
                subscriptionsUrl= "https://api.github.com/users/alimohamadi17/subscriptions",
                organizationsUrl= "https://api.github.com/users/alimohamadi17/orgs",
                reposUrl= "https://api.github.com/users/alimohamadi17/repos",
                eventsUrl= "https://api.github.com/users/alimohamadi17/events{/privacy}",
                receivedEventsUrl= "https://api.github.com/users/alimohamadi17/received_events",
                type= "User",
                siteAdmin= false,
                name= "Ayodeji",
                company= null,
                blog= "",
                location= "",
                email= null,
                hireable= null,
                bio= null,
                twitterUsername= null,
                publicRepos= 16,
                publicGists= 0,
                followers= 398,
                following= 702,
                createdAt= "2020-10-15T08:06:19Z",
                updatedAt= "2021-09-26T20:14:21Z"
            )
        }

        private fun createUserReposList() = arrayListOf(
            DeveloperRepoResultItem(
                name= "alimohamadi17",
                stargazersCount= 1,
                allowForking= true,
                forks= 0,
            )
        )

        fun fetchUsers(): FetchDataModel {
            return FetchDataModel(
                page = 1,
                usersInfo = createUsersResultsList()
            )
        }

        private fun createUsersResultsList() = listOf(
            DeveloperInfo(
                login = "alimate",
                id = 696139,
                nodeId = "MDQ6VXNlcjY5NjEzOQ==",
                avatarUrl = "https://avatars.githubusercontent.com/u/696139?v=4",
                gravatarId = "",
                url = "https://api.github.com/users/alimate",
                htmlUrl = "https://github.com/alimate",
                followersUrl = "https://api.github.com/users/alimate/followers",
                followingUrl = "https://api.github.com/users/alimate/following{/other_user}",
                gistsUrl = "https://api.github.com/users/alimate/gists{/gist_id}",
                starredUrl = "https://api.github.com/users/alimate/starred{/owner}{/repo}",
                subscriptionsUrl = "https://api.github.com/users/alimate/subscriptions",
                organizationsUrl = "https://api.github.com/users/alimate/orgs",
                reposUrl = "https://api.github.com/users/alimate/repos",
                eventsUrl = "https://api.github.com/users/alimate/events{/privacy}",
                receivedEventsUrl = "https://api.github.com/users/alimate/received_events",
                type = "User",
                siteAdmin = false,
                score = 1.0,
                developerRepoResult = emptyList(),
                developerData = null,

            )
        )


    }
}
