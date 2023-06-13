package techinterviewbot.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import techinterviewbot.data.source.mock.MockInterviewSource
import techinterviewbot.interview.api.host.DurationMode
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener

class InterviewerHostImplTest : KoinTest {

    private val emptyListener = object : TechInterviewEventListener {
        override fun onEvent(event: TechInterviewEvent) {

        }
    }

    @Test
    fun `Check "start" emits first question`() {
        val interviewerHelper = InterviewerHostImpl(source = MockInterviewSource(), listener = emptyListener)

        val subTopics = listOf("SubTopic1", "SubTopic2")

        interviewerHelper.start(subTopics, durationMode = DurationMode.SHORT)

        assertEquals(interviewerHelper.techInterview.questions.first(), interviewerHelper.state.value.question)
    }

    @Test
    fun `Check "nextQuestion" switches to next question from list`() {
        val interviewerHelper = InterviewerHostImpl(source = MockInterviewSource(), emptyListener)

        val subTopics = listOf("SubTopic1", "SubTopic2")

        interviewerHelper.start(subTopics, durationMode = DurationMode.SHORT)
        interviewerHelper.nextQuestion()

        val expected = interviewerHelper.techInterview.questions[1]
        val actual = interviewerHelper.state.value.question
        assertEquals(expected, actual)
    }

    @Test
    fun `Check "prevQuestion" switches to previous question from list`() {
        val interviewerHelper = InterviewerHostImpl(source = MockInterviewSource(), emptyListener)

        val subTopics = listOf("SubTopic1", "SubTopic2")

        interviewerHelper.start(subTopics, durationMode = DurationMode.SHORT)

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

        val subTopics = listOf("SubTopic1", "SubTopic2")

        interviewerHelper.start(subTopics, durationMode = DurationMode.SHORT)

        interviewerHelper.navigateToQuestion(interviewerHelper.techInterview.questions.lastIndex)
        interviewerHelper.nextQuestion()

        assertEquals(true, testResult)
    }

}