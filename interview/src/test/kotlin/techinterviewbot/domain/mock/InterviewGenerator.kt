package techinterviewbot.domain.mock

import techinterviewbot.domain.models.Question
import techinterviewbot.domain.models.TechInterview

class InterviewGenerator {

    fun generate(): TechInterview {
        return TechInterview(generateQuestions())
    }

    private fun generateQuestions(): List<Question> {
        val questions = mutableListOf<Question>()

        val topics = QuestionTopic.values()
        val categories = QuestionCategory.values()

        for (topic in topics) {
            for (category in categories) {
                val questionText = generateQuestionText(topic, category)
                val question = Question(questionText, topic.name, category.name)
                questions.add(question)
            }
        }

        return questions
    }

    private fun generateQuestionText(topic: QuestionTopic, category: QuestionCategory): String {
        // Generate question text based on the topic and category
        return "What is the importance of ${topic.name} in ${category.name.toLowerCase()} in computer science?"
    }
}