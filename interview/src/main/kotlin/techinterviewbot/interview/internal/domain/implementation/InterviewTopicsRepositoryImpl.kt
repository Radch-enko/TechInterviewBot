package techinterviewbot.interview.internal.domain.implementation

import techinterviewbot.interview.internal.domain.InterviewTopicsRepository

internal class InterviewTopicsRepositoryImpl : InterviewTopicsRepository {

    private val topics = mapOf(
        "Computer science" to listOf(
            "Algorithms",
            "Network",
            "Database"
        ),
        "Android" to listOf("Android Components", "Coroutines", "Kotlin"),
    )

    override fun getTopics(): List<String> {
        return topics.keys.toList()
    }

    override fun getSubTopics(topics: List<String>): List<String> {
        return this.topics.filterKeys { topics.contains(it) }.values.flatten()
    }
}