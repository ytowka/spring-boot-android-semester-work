package com.danilkha.conentfrientdsclient.features.app

object NavDestinations {

    const val AUTH = "auth"
    const val TOPIC_LIST = "topic_list"
    const val USER_ADMIN_LIST = "user_admin_list"
    const val USER_SEARCH = "user_search"

    object TopicContent{
        const val topicIdArg = "topicId"
        const val destination = "topic_content/{$topicIdArg}"
        operator fun invoke(topicId: Long) = "topic_content/$topicId"
    }
    object ReviewList{
        const val contentIdArg = "contentId"
        const val destination = "content_reviews/{$contentIdArg}"
        operator fun invoke(contentId: Long) = "content_reviews/$contentId"
    }

    object ReviewEditor{
        const val contentIdArg = "contentId"
        const val destination = "review_editor/{$contentIdArg}"
        operator fun invoke(contentId: Long) = "review_editor/$contentId"
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