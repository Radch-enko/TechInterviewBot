package techinterviewbot.interview.internal.domain

import techinterviewbot.interview.internal.domain.models.Question

internal interface InterviewQuestionsRepository {
    fun getQuestions(topics: List<String>, subTopics: List<String>): List<Question>
}