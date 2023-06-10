import kotlin.random.Random
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import techinterviewbot.app.mock.MockTechInterview
import techinterviewbot.app.prettyPrintReport
import techinterviewbot.domain.InterviewerHostImpl
import techinterviewbot.domain.TechInterviewEvent
import techinterviewbot.domain.TechInterviewEventListener
import techinterviewbot.domain.models.AnswerGrade
import techinterviewbot.utilities.StringUtil
import techinterviewbot.utilities.StringValues

class TelegramUIExtentionsKtTest {

    @Test
    fun `Pretty print report`() {
        var actual: String? = null
        val interview = MockTechInterview.generate()
        val host = InterviewerHostImpl(interview, object : TechInterviewEventListener {
            override fun onEvent(event: TechInterviewEvent) {
                when (event) {
                    is TechInterviewEvent.Finish -> actual = prettyPrintReport(event)
                    TechInterviewEvent.Start -> {

                    }
                }
            }
        })

        host.start()

        // 1
        host.rateAnswerGrade(grade = AnswerGrade.IncompleteKnowledge)
        host.nextQuestion()

        // 2
        host.rateAnswerGrade(grade = AnswerGrade.NoExperience)
        host.nextQuestion()

        // 3
        host.rateAnswerGrade(grade = AnswerGrade.Note("some note"))
        host.nextQuestion()

        // 4
        host.rateAnswerGrade(grade = AnswerGrade.GoodAnswer)
        host.nextQuestion()

        // 5
        host.rateAnswerGrade(grade = AnswerGrade.GoodAnswer)
        host.nextQuestion()

        // 6
        host.rateAnswerGrade(grade = AnswerGrade.GoodAnswer)
        host.nextQuestion()

        // 7
        host.rateAnswerGrade(grade = AnswerGrade.GoodAnswer)
        host.nextQuestion()

        // 8
        host.rateAnswerGrade(grade = AnswerGrade.GoodAnswer)
        host.nextQuestion()

        // 9
        host.rateAnswerGrade(grade = AnswerGrade.NoExperience)
        host.nextQuestion()

        // 10
        host.rateAnswerGrade(grade = AnswerGrade.GoodAnswer)
        host.nextQuestion()

        val expect = """
            Итоговый отчет по кандидату:
            ---------------------------
            1. What is an algorithm? - Неглубокие знания
            2. What is the difference between TCP and UDP? - Нет опыта
            3. What is a primary key in a database? - some note
            4. What is the time complexity of quicksort algorithm? - Отлично
            5. What is the purpose of an index in a database? - Отлично
            6. What is the OSI model in computer networking? - Отлично
            7. What is a foreign key in a database? - Отлично
            8. What is the difference between a stack and a queue? - Отлично
            9. What is the role of DNS in computer networks? - Нет опыта
            10. What is the ACID property in database transactions? - Отлично
            ---------------------------
        """.trimIndent()

        assertEquals(expect, actual)
    }

    @Test
    fun test() {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        fun randomStringByKotlinRandom() = (1..50)
            .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
            .joinToString("")

        val interview = MockTechInterview.generate()

        val gradeVariants = listOf(
            AnswerGrade.GoodAnswer,
            AnswerGrade.NoExperience,
            AnswerGrade.IncompleteKnowledge,
            AnswerGrade.Note(randomStringByKotlinRandom())
        )

        val result = interview.questions.mapIndexed { index, question ->
            "${index + 1}. ${question.text} - ${
                when (val selectedGrade = gradeVariants.random()) {
                    is AnswerGrade.Note -> selectedGrade.text
                    AnswerGrade.GoodAnswer -> StringUtil.getString(StringValues.Perfect)
                    AnswerGrade.IncompleteKnowledge -> StringUtil.getString(StringValues.IncompleteKnowledge)
                    AnswerGrade.NoExperience -> StringUtil.getString(StringValues.Inexperience)
                }
            }\n"
        }
        println(result)
    }
}