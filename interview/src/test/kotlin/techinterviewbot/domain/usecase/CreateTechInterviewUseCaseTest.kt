package techinterviewbot.domain.usecase

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.junit5.KoinTestExtension
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener
import techinterviewbot.interview.api.source.MockInterviewSource
import techinterviewbot.interview.internal.domain.InterviewQuestionsRepository
import techinterviewbot.interview.internal.domain.InterviewTopicsRepository
import techinterviewbot.interview.internal.domain.implementation.InterviewQuestionsRepositoryImpl
import techinterviewbot.interview.internal.domain.implementation.InterviewTopicsRepositoryImpl
import techinterviewbot.interview.internal.domain.models.Question
import techinterviewbot.interview.internal.domain.usecase.CreateTechInterviewUseCase

class CreateTechInterviewUseCaseTest {

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                factory<InterviewTopicsRepository> { InterviewTopicsRepositoryImpl() }
                factory<InterviewQuestionsRepository> { InterviewQuestionsRepositoryImpl() }
            })
    }

    @Test
    fun `Questions must be sorted by topic`() {
        val interviewerHelper =
            InterviewerHostImpl(source = MockInterviewSource(), object : TechInterviewEventListener {
                override fun onEvent(event: TechInterviewEvent) {

                }
            })

        val topics = listOf("Computer science", "Android")
        val subTopics = listOf("Algorithms", "Kotlin", "Network", "Common")
        interviewerHelper.start(topics, subTopics)
        val createTechInterviewUseCase = CreateTechInterviewUseCase(interviewerHelper.techInterview.questions)
        val expected = listOf(
            Question(
                "Расскажи алгоритм быстрой сортировки",
                "Computer science",
                "Algorithms",
            ),
            Question(
                "Расскажи про протокол TCP/IP. Как он работает?",
                "Computer science",
                "Network",
            ),
            Question(
                "Перечисли методы жизненного цикла Activity",
                "Android",
                "Common"
            ),
            Question(
                "Какого вида исключения представлены в Kotlin?",
                "Android",
                "Kotlin"
            )
        )
        val actual = createTechInterviewUseCase().questions
        assertEquals(actual = actual, expected = expected)
    }
}