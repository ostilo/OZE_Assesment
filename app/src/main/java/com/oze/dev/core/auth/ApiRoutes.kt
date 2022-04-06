package com.oze.dev.core.auth

import com.oze.dev.utils.CONSTANTS

object ApiRoutes {
    const val GET_SEARCH_DEVELOPER_DATA = "search/users?per_page=30"
    const val GET_SPECIFIC_DEVELOPER_DATA = "users/{${CONSTANTS.DEVELOPER}}"
    const val GET_SPECIFIC_DEVELOPER_REPOS = "users/{${CONSTANTS.DEVELOPER}}/repos?per_page=20"

}