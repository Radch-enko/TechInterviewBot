package techinterviewbot.domain

import org.junit.Ignore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import techinterviewbot.data.source.excel.ExcelDataSource
import techinterviewbot.interview.api.host.DurationMode
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener
import techinterviewbot.interview.internal.domain.models.SubtopicDomain

// TODO(radchenko): Update outdated tests. Implement MockInterviewSource
@Ignore
class InterviewerHostImplTest : KoinTest {

    companion object {
        const val EXCEL_FILE_PATH =
            "source.xlsx"
    }

    private val sourceFile = Thread.currentThread().contextClassLoader.getResourceAsStream(EXCEL_FILE_PATH)

    private val emptyListener = object : TechInterviewEventListener {
        override fun onEvent(event: TechInterviewEvent) {

        }
    }

    @Test
    fun `Check "start" emits first question`() {
        val interviewerHelper = InterviewerHostImpl(source = ExcelDataSource(sourceFile), listener = emptyListener)

        val subTopics = listOf(SubtopicDomain(id = 1))

        interviewerHelper.possibleSubTopics = subTopics

        interviewerHelper.start()

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
        val source = ExcelDataSource()
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