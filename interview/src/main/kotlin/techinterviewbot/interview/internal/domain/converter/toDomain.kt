package techinterviewbot.interview.internal.domain.converter

import techinterviewbot.data.dto.SubtopicDTO
import techinterviewbot.data.dto.TechInterviewDTO
import techinterviewbot.interview.internal.domain.models.Question
import techinterviewbot.interview.internal.domain.models.SubtopicDomain
import techinterviewbot.interview.internal.domain.models.TechInterview
import techinterviewbot.interview.internal.domain.models.TopicDomain

internal fun TechInterviewDTO.toDomain(): TechInterview {
    return TechInterview(
        questions = this.questions.map { dto ->
            Question(
                text = dto.text,
                topic = TopicDomain(
                    id = dto.topic!!.id,
                    name = dto.topic!!.name
                ),
                subTopic = SubtopicDomain(
                    id = dto.subTopic!!.id,
                    name = dto.subTopic!!.name,
                    topicId = dto.subTopic!!.topicId
                ),
                answer = dto.answer,
                category = dto.category
            )
        }
    )
}

internal fun List<SubtopicDomain>.toDTO(): List<SubtopicDTO> {
    return this.map { domain ->
        SubtopicDTO(
            id = domain.id,
            name = domain.name,
            topicId = domain.topicId
        )
    }
}