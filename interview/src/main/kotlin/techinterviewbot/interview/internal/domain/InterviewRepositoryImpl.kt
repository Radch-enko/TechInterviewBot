package techinterviewbot.interview.internal.domain

import techinterviewbot.data.dto.SubtopicDTO
import techinterviewbot.data.source.InterviewSource
import techinterviewbot.interview.internal.domain.converter.toDomain
import techinterviewbot.interview.internal.domain.models.SubtopicDomain
import techinterviewbot.interview.internal.domain.models.TechInterview
import techinterviewbot.interview.internal.domain.models.TopicDomain

public class InterviewRepositoryImpl(private val interviewSource: InterviewSource) : InterviewRepository {
    override fun getTopics(): List<TopicDomain> {
        return interviewSource.getTopics().map { dto ->
            TopicDomain(
                id = dto.id,
                name = dto.name
            )
        }
    }

    override fun getSubTopics(): List<SubtopicDomain> {
        return interviewSource.getSubTopics().map { dto ->
            SubtopicDomain(
                id = dto.id,
                name = dto.name,
                topicId = dto.topicId
            )
        }
    }

    override fun generateTechInterview(
        countQuestionInSubtopic: Int,
        selectedSubTopics: List<SubtopicDomain>
    ): TechInterview {
        return interviewSource.generateTechInterview(
            countQuestionInSubtopic = countQuestionInSubtopic,
            selectedSubTopics = selectedSubTopics.map { domain ->
                SubtopicDTO(
                    id = domain.id,
                    name = domain.name,
                    topicId = domain.topicId
                )
            }
        ).toDomain()
    }
}