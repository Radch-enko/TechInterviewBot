package techinterviewbot.interview.internal.domain.implementation

import techinterviewbot.interview.internal.domain.InterviewTopicsRepository

internal class InterviewTopicsRepositoryImpl : InterviewTopicsRepository {
    override fun getTopics(): List<String> {
        return listOf(
            "Computer science",
            "Android"
        )
    }

    override fun getSubTopics(): List<String> {
        return listOf(
            "Algorithms",
            "Network",
            "Database",
            "Android Components",
            "Coroutines",
            "Kotlin"
        )
    }

}