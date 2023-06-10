package techinterviewbot.interview.internal.domain

internal interface InterviewTopicsRepository {
    fun getTopics(): List<String>
    fun getSubTopics(topics: List<String>): List<String>
}