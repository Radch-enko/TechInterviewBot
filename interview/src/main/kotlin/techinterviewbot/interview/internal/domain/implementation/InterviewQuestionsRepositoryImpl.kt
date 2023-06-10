package techinterviewbot.interview.internal.domain.implementation

import techinterviewbot.interview.internal.domain.InterviewQuestionsRepository
import techinterviewbot.interview.internal.domain.models.Question

public class InterviewQuestionsRepositoryImpl : InterviewQuestionsRepository {

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

    override fun getQuestions(topics: List<String>, subTopics: List<String>): List<Question> {
        return mockData.filter { topics.contains(it.topic) }.filter { subTopics.contains(it.subTopic) }
    }
}