package techinterviewbot.interview.internal.domain.converter

import techinterviewbot.data.dto.TechInterviewDTO
import techinterviewbot.interview.internal.domain.models.Question
import techinterviewbot.interview.internal.domain.models.TechInterview

internal fun TechInterviewDTO.toDomain(): TechInterview {
    return TechInterview(
        questions = this.questions.map { dto ->
            Question(
                text = dto.text ?: "ERROR",
                topic = dto.topic ?: "ERROR",
                subTopic = dto.subTopic ?: "ERROR",
                answer = dto.answer,
                category = dto.category
            )
        }
    )
}