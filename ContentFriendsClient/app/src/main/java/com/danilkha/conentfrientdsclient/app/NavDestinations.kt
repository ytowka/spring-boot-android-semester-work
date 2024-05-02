package com.danilkha.conentfrientdsclient.app

object NavDestinations {

    const val AUTH = "auth"
    const val TOPIC_LIST = "topic_list"
    const val USER_ADMIN_LIST = "user_admin_list"
    const val USER_SEARCH = "user_search"

    object TopicContent{
        const val topicIdArg = "userid"
        const val destination = "topic_content/{$topicIdArg}"
        operator fun invoke(topicId: Long) = "topic_content/$topicId"
    }
    object UserDetails {
        val userIdArg = "userid"
        val destination = "user_details/{$userIdArg}"
        operator fun invoke(userid: String) = "user_details/$userid"
    }
    object UserProfile {
        val userIdArg = "userid"
        val destination = "user_profile/{$userIdArg}"
        operator fun invoke(userid: String) = "user_profile/$userid"
    }
}