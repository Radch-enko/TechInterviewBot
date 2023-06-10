package techinterviewbot.domain.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import techinterviewbot.domain.mock.InterviewGenerator

class CreateTechInterviewUseCaseTest {

    @Test
    fun `Questions must be sorted by topic`() {
        val questions = InterviewGenerator().generate().questions
        val createTechInterviewUseCase = CreateTechInterviewUseCase(questions)
        assertEquals(createTechInterviewUseCase().questions, questions)
    }
}