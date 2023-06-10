package techinterviewbot.interview.internal.domain.usecase

import techinterviewbot.interview.internal.domain.models.Question
import techinterviewbot.interview.internal.domain.models.TechInterview

internal class CreateTechInterviewUseCase(private val questions: List<Question>) {

    operator fun invoke(): TechInterview {
        return TechInterview(
            questions = compileList(questions)
        )
    }

    private fun compileList(questions: List<Question>): List<Question> {
        return questions.groupBy { it.topic }.flatMap { it.value }.groupBy { it.subTopic }.flatMap { it.value }
    }
}