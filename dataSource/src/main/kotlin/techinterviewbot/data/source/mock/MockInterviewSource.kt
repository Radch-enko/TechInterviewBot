package techinterviewbot.data.source.mock

import techinterviewbot.data.dto.QuestionDTO
import techinterviewbot.data.dto.TechInterviewDTO
import techinterviewbot.data.source.InterviewSource

public class MockInterviewSource : InterviewSource {

    private var allSubTopics: Map<String, List<QuestionDTO>>? = null

    private val rootDb = mapOf(
        "Topic1" to mapOf(
            "SubTopic1" to listOf(
                QuestionDTO(
                    text = "Question 1",
                    topic = "Topic1",
                    "SubTopic1",
                    answer = "Answer 1",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 2",
                    topic = "Topic1",
                    "SubTopic1",
                    answer = "Answer 2",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 3",
                    topic = "Topic1",
                    "SubTopic1",
                    answer = "Answer 3",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 4",
                    topic = "Topic1",
                    "SubTopic1",
                    answer = "Answer 4",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 5",
                    topic = "Topic1",
                    "SubTopic1",
                    answer = "Answer 5",
                    category = "Category 1"
                )
            ),
            "SubTopic2" to listOf(
                QuestionDTO(
                    text = "Question 6",
                    topic = "Topic1",
                    "SubTopic2",
                    answer = "Answer 6",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 7",
                    topic = "Topic1",
                    "SubTopic2",
                    answer = "Answer 7",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 8",
                    topic = "Topic1",
                    "SubTopic2",
                    answer = "Answer 8",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 9",
                    topic = "Topic1",
                    "SubTopic2",
                    answer = "Answer 9",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 10",
                    topic = "Topic1",
                    "SubTopic2",
                    answer = "Answer 10",
                    category = "Category 1"
                )
            ),
            "SubTopic3" to listOf(
                QuestionDTO(
                    text = "Question 11",
                    topic = "Topic1",
                    "SubTopic3",
                    answer = "Answer 11",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 12",
                    topic = "Topic1",
                    "SubTopic3",
                    answer = "Answer 12",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 13",
                    topic = "Topic1",
                    "SubTopic3",
                    answer = "Answer 13",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 14",
                    topic = "Topic1",
                    "SubTopic3",
                    answer = "Answer 14",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 15",
                    topic = "Topic1",
                    "SubTopic3",
                    answer = "Answer 15",
                    category = "Category 1"
                )
            ),
        ),
        "Topic2" to mapOf(
            "SubTopic4" to listOf(
                QuestionDTO(
                    text = "Question 1",
                    topic = "Topic2",
                    "SubTopic1",
                    answer = "Answer 1",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 2",
                    topic = "Topic2",
                    "SubTopic1",
                    answer = "Answer 2",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 3",
                    topic = "Topic2",
                    "SubTopic1",
                    answer = "Answer 3",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 4",
                    topic = "Topic2",
                    "SubTopic1",
                    answer = "Answer 4",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 5",
                    topic = "Topic2",
                    "SubTopic1",
                    answer = "Answer 5",
                    category = "Category 1"
                )
            ),
            "SubTopic5" to listOf(
                QuestionDTO(
                    text = "Question 6",
                    topic = "Topic2",
                    "SubTopic2",
                    answer = "Answer 6",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 7",
                    topic = "Topic2",
                    "SubTopic2",
                    answer = "Answer 7",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 8",
                    topic = "Topic2",
                    "SubTopic2",
                    answer = "Answer 8",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 9",
                    topic = "Topic2",
                    "SubTopic2",
                    answer = "Answer 9",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 10",
                    topic = "Topic2",
                    "SubTopic2",
                    answer = "Answer 10",
                    category = "Category 1"
                )
            ),
            "SubTopic6" to listOf(
                QuestionDTO(
                    text = "Question 11",
                    topic = "Topic2",
                    "SubTopic3",
                    answer = "Answer 11",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 12",
                    topic = "Topic2",
                    "SubTopic3",
                    answer = "Answer 12",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 13",
                    topic = "Topic2",
                    "SubTopic3",
                    answer = "Answer 13",
                    category = "Category 2"
                ),
                QuestionDTO(
                    text = "Question 14",
                    topic = "Topic2",
                    "SubTopic3",
                    answer = "Answer 14",
                    category = "Category 1"
                ),
                QuestionDTO(
                    text = "Question 15",
                    topic = "Topic2",
                    "SubTopic3",
                    answer = "Answer 15",
                    category = "Category 1"
                )
            ),
        ),
    )

    override fun getTopics(): List<String> {
        return rootDb.keys.toList()
    }

    override fun getSubTopics(selectedTopics: List<String>): List<String> {
        return rootDb.filter { selectedTopics.contains(it.key) }.values.map {
            allSubTopics = it
            it.keys
        }.flatten()
    }

    override fun generateTechInterview(
        countQuestionInSubtopic: Int,
        selectedSubTopics: List<String>
    ): TechInterviewDTO {

        val questionsList = mutableListOf<QuestionDTO>()
        allSubTopics?.filter { subTopicPage ->
            selectedSubTopics.contains(subTopicPage.key)
        }?.forEach { subTopicPage ->
            repeat(countQuestionInSubtopic) {
                questionsList.addRandomQuestion(subTopicPage.value)
            }
        }

        return TechInterviewDTO(
            questions = questionsList
        )
    }

    private fun MutableList<QuestionDTO>.addRandomQuestion(
        questionsOfSubTopic: List<QuestionDTO>
    ) {
        val randomQuestion = questionsOfSubTopic.random()
        add(randomQuestion)
    }

}
