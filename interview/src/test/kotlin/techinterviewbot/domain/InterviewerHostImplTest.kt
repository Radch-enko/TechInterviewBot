package techinterviewbot.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener
import techinterviewbot.interview.api.source.MockInterviewSource
import techinterviewbot.interview.internal.domain.InterviewQuestionsRepository
import techinterviewbot.interview.internal.domain.InterviewTopicsRepository
import techinterviewbot.interview.internal.domain.implementation.InterviewQuestionsRepositoryImpl
import techinterviewbot.interview.internal.domain.implementation.InterviewTopicsRepositoryImpl

class InterviewerHostImplTest : KoinTest {

    private val emptyListener = object : TechInterviewEventListener {
        override fun onEvent(event: TechInterviewEvent) {

        }
    }

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
    fun `Check "start" emits first question`() {
        val interviewerHelper = InterviewerHostImpl(source = MockInterviewSource(), listener = emptyListener)

        val topics = listOf("Computer science", "Android")
        val subTopics = listOf("Algorithms", "Kotlin")

        interviewerHelper.start(topics, subTopics)

        assertEquals(interviewerHelper.techInterview.questions.first(), interviewerHelper.state.value.question)
    }

    @Test
    fun `Check "nextQuestion" switches to next question from list`() {
        val interviewerHelper = InterviewerHostImpl(source = MockInterviewSource(), emptyListener)

        val topics = listOf("Computer science", "Android")
        val subTopics = listOf("Algorithms", "Kotlin")

        interviewerHelper.start(topics, subTopics)
        interviewerHelper.nextQuestion()

        val expected = interviewerHelper.techInterview.questions[1]
        val actual = interviewerHelper.state.value.question
        assertEquals(expected, actual)
    }

    @Test
    fun `Check "prevQuestion" switches to previous question from list`() {
        val interviewerHelper = InterviewerHostImpl(source = MockInterviewSource(), emptyListener)

        val topics = listOf("Computer Science", "Android")
        val subTopics = listOf("Algorithms", "Kotlin")
        interviewerHelper.start(topics, subTopics)

        interviewerHelper.nextQuestion()
        interviewerHelper.prevQuestion()

        val expected = interviewerHelper.techInterview.questions[0]
        val actual = interviewerHelper.state.value.question
        assertEquals(expected, actual)
    }

    @Test
    fun `Check "nextQuestion" on the last question`() {
        var testResult = false
        val source = MockInterviewSource()
        val interviewerHelper =
            InterviewerHostImpl(source = source, object : TechInterviewEventListener {
                override fun onEvent(event: TechInterviewEvent) {
                    when (event) {
                        is TechInterviewEvent.Finish -> {
                            testResult = true
                        }

                        TechInterviewEvent.Start -> {}
                    }
                }
            })

        val selectedTopics = listOf("Computer Science", "Android")
        val selectedSubTopics = listOf("Algorithms", "Kotlin")

        interviewerHelper.start(selectedTopics, selectedSubTopics)

        interviewerHelper.navigateToQuestion(interviewerHelper.techInterview.questions.lastIndex)
        interviewerHelper.nextQuestion()

        assertEquals(true, testResult)
    }

}