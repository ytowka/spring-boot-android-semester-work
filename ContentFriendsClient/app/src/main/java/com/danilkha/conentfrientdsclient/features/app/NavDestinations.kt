package com.danilkha.conentfrientdsclient.features.app

object NavDestinations {

    const val AUTH = "auth"
    const val TOPIC_LIST = "topic_list"
    const val USER_ADMIN_LIST = "user_admin_list"
    const val USER_SEARCH = "user_search"
    const val MY_ACCOUNT = "my_account"

    object TopicContent{
        const val topicIdArg = "topicId"
        const val topicNameArg = "topicName"
        const val destination = "topic_content/{$topicIdArg}/{$topicNameArg}"
        operator fun invoke(topicId: Long, topicName: String) = "topic_content/$topicId/$topicName"
    }
    object ReviewList{
        const val contentIdArg = "contentId"
        const val contentNameArg = "contentNeme"
        const val destination = "content_reviews/{$contentIdArg}/{$contentNameArg}"
        operator fun invoke(contentId: Long, contentName: String) = "content_reviews/$contentId/$contentName"
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