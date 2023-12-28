pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Films Android App"
include(":app")
include(":core")
include(":feature")


include(":core:widget")
include(":core:designsystem")

include(":feature:auth")
include(":feature:auth:api")
include(":feature:auth:impl")

include(":feature:home")
include(":feature:home:api")
include(":feature:home:impl")

include(":feature:profile")

