rootProject.name = "ContentFriends"
include(":ContentFriendsBackend")
include(":ContentFriendsApi")
include(":ContentFriendsClient")
project(":ContentFriendsClient").projectDir = file("ContentFriendsClient/app")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
