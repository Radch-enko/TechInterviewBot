import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.junit5.KoinTestExtension
import techinterviewbot.app.prettyPrintReport
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener
import techinterviewbot.interview.api.source.MockInterviewSource
import techinterviewbot.interview.internal.domain.di.interviewDIModule
import techinterviewbot.interview.internal.domain.models.AnswerGrade

class TelegramUIExtentionsKtTest {

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(interviewDIModule)
    }

    @Test
    fun `Pretty print report`() {
        var actual: String? = null

        val topics = listOf("Computer science", "Android")
        val subTopics = listOf("Algorithms", "Kotlin", "Common", "Network")

        val host = InterviewerHostImpl(source = MockInterviewSource(), object : TechInterviewEventListener {
            override fun onEvent(event: TechInterviewEvent) {
                when (event) {
                    is TechInterviewEvent.Finish -> actual = prettyPrintReport(event)
                    TechInterviewEvent.Start -> {

                    }
                }
            }
        })

        host.start(topics, subTopics)

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

        val expect = """
            Итоговый отчет по кандидату:
            ---------------------------
            1. Расскажи алгоритм быстрой сортировки - Неглубокие знания
            2. Перечисли методы жизненного цикла Activity - Нет опыта
            3. Расскажи про протокол TCP/IP. Как он работает? - some note
            4. Какого вида исключения представлены в Kotlin? - Отлично
            ---------------------------
        """.trimIndent()

        assertEquals(expect, actual)
    }
}