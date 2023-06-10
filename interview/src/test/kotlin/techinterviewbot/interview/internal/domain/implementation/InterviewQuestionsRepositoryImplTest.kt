package techinterviewbot.interview.internal.domain.implementation

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import techinterviewbot.interview.internal.domain.models.Question

class InterviewQuestionsRepositoryImplTest {

    private val mockData = listOf(
        Question(
            "Расскажи алгоритм быстрой сортировки",
            "Computer science",
            "Algorithms",
        ),
        Question(
            "Перечисли методы жизненного цикла Activity",
            "Android",
            "Common"
        ),
        Question(
            "Расскажи про протокол TCP/IP. Как он работает?",
            "Computer science",
            "Network",
        ),
        Question(
            "Какого вида исключения представлены в Kotlin?",
            "Android",
            "Kotlin"
        )
    )

    @Test
    fun `Filter questions by "topics" and "subTopics"`() {
        val topics = listOf("Computer science", "Android")
        val subTopics = listOf("Algorithms", "Common")
        val expect = listOf(
            Question(
                "Расскажи алгоритм быстрой сортировки",
                "Computer science",
                "Algorithms",
            ),
            Question(
                "Перечисли методы жизненного цикла Activity",
                "Android",
                "Common"
            )
        )
        val actual = mockData.filter { topics.contains(it.topic) }.filter { subTopics.contains(it.subTopic) }
        assertEquals(expect, actual)
    }
}