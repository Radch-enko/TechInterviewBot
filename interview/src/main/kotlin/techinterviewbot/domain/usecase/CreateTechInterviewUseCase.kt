package techinterviewbot.domain.usecase

import techinterviewbot.domain.models.Question
import techinterviewbot.domain.models.TechInterview

internal class CreateTechInterviewUseCase(private val questions: List<Question>) {

    operator fun invoke(): TechInterview {
        return TechInterview(
            questions = compileList(questions)
        )
    }

    private fun compileList(questions: List<Question>): List<Question> {
        return questions.sortedBy { it.topic } // TODO(radchenko): Improve algorithm of generation to be more flexible
    }
}