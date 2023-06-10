package techinterviewbot.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import techinterviewbot.domain.mock.InterviewGenerator

class InterviewerHostImplTest {

    private val emptyListener = object : TechInterviewEventListener {
        override fun onEvent(event: TechInterviewEvent) {

        }
    }

    @Test
    fun `Check "start" emits first question`() {
        val mockInterview = InterviewGenerator()
        val interview = mockInterview.generate()
        val interviewerHelper = InterviewerHostImpl(techInterview = interview, emptyListener)

        interviewerHelper.start()

        assertEquals(interview.questions.first(), interviewerHelper.state.value.question)
    }

    @Test
    fun `Check "nextQuestion" switches to next question from list`() {
        val mockInterview = InterviewGenerator()
        val interview = mockInterview.generate()
        val interviewerHelper = InterviewerHostImpl(techInterview = interview, emptyListener)

        interviewerHelper.start()
        interviewerHelper.nextQuestion()

        val expected = interview.questions[1]
        val actual = interviewerHelper.state.value.question
        assertEquals(expected, actual)
    }

    @Test
    fun `Check "prevQuestion" switches to previous question from list`() {
        val mockInterview = InterviewGenerator()
        val interview = mockInterview.generate()
        val interviewerHelper = InterviewerHostImpl(techInterview = interview, emptyListener)

        interviewerHelper.start()

        interviewerHelper.nextQuestion()
        interviewerHelper.prevQuestion()

        val expected = interview.questions[0]
        val actual = interviewerHelper.state.value.question
        assertEquals(expected, actual)
    }

    @Test
    fun `Check "nextQuestion" on the last question`() {
        var testResult = false
        val mockInterview = InterviewGenerator()
        val interview = mockInterview.generate()
        val interviewerHelper = InterviewerHostImpl(techInterview = interview, object : TechInterviewEventListener {
            override fun onEvent(event: TechInterviewEvent) {
                when(event){
                    TechInterviewEvent.Finish -> { testResult = true }
                    TechInterviewEvent.Start -> {}
                }
            }
        })

        interviewerHelper.start()

        interviewerHelper.navigateToQuestion(interview.questions.lastIndex)
        interviewerHelper.nextQuestion()

        assertEquals(true, testResult)
    }

}