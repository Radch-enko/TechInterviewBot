package techinterviewbot.interview.api.source

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import techinterviewbot.interview.internal.domain.InterviewQuestionsRepository
import techinterviewbot.interview.internal.domain.InterviewTopicsRepository
import techinterviewbot.interview.internal.domain.models.TechInterview

public class MockInterviewSource : InterviewSource, KoinComponent {

    private val topicsRepository: InterviewTopicsRepository by inject()
    private val questionsRepository: InterviewQuestionsRepository by inject()
    override fun getTopics(): List<String> = topicsRepository.getTopics()
    override fun getSubTopics(selectedTopics: List<String>): List<String> =
        topicsRepository.getSubTopics(selectedTopics)

    override fun generateTechInterview(topics: List<String>, subTopics: List<String>): TechInterview {
        return TechInterview(
            questions = questionsRepository.getQuestions(topics, subTopics)
        )
    }
}