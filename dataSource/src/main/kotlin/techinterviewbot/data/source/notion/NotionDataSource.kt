package techinterviewbot.data.source.notion

import notion.api.v1.NotionClient
import notion.api.v1.http.HttpUrlConnNotionHttpClient
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageProperty
import techinterviewbot.data.dto.QuestionDTO
import techinterviewbot.data.dto.TechInterviewDTO
import techinterviewbot.data.source.InterviewSource

public class NotionDataSource(token: String, private val databaseId: String) : InterviewSource {

    private val notionClient = NotionClient(
        token = token,
        httpClient = HttpUrlConnNotionHttpClient(readTimeoutMillis = 90_000, connectTimeoutMillis = 30_000)
    )

    internal data class TopicPage(
        val id: String,
        val properties: Map<String, PageProperty>
    )

    internal data class SubTopicPage(
        val id: String,
        val topicName: String?,
        val properties: Map<String, PageProperty>
    )

    private fun Page.toTopicPage(): TopicPage {
        return TopicPage(
            id = this.id,
            properties = this.properties
        )
    }


    private val topics: MutableMap<TopicPage, List<Page>> = mutableMapOf()
    private var hostSelectedTopics: List<SubTopicPage> = emptyList()

    init {
        notionClient.queryDatabase(databaseId).results.forEach { topicPage ->
            topics[topicPage.toTopicPage()] =
                notionClient.retrieveBlockChildren(topicPage.id).results.filter { it.type.value == "child_database" }
                    .mapNotNull { block ->
                        block.id?.let {
                            notionClient.queryDatabase(it).results
                        }
                    }.flatten()
        }
    }

    public override fun getTopics(): List<String> {
        return topics.keys.mapNotNull { page ->
            page.properties["Topic"]?.title
        }.flatten().mapNotNull { property ->
            property.plainText
        }
    }

    public override fun getSubTopics(selectedTopics: List<String>): List<String> {
        return getSubTopicsPages(selectedTopics).mapNotNull { page ->
            page.properties["Subtopic"]?.title?.mapNotNull { field -> field.plainText }
        }.flatten()
    }

    private fun getSubTopicsPages(selectedTopics: List<String>): List<SubTopicPage> {
        val subTopics = topics.filterKeys { page ->
            selectedTopics.contains(page.properties["Topic"]?.title?.first()?.plainText)
        }

        return subTopics.map { entry ->
            entry.value.map { page ->
                SubTopicPage(
                    id = page.id,
                    topicName = entry.key.properties["Topic"]?.title?.first()?.plainText,
                    properties = page.properties
                )
            }
        }.flatten().also {
            this.hostSelectedTopics = it
        }
    }

    override fun generateTechInterview(
        countQuestionInSubtopic: Int,
        selectedSubTopics: List<String>
    ): TechInterviewDTO {
        val questions = mutableListOf<QuestionDTO>()
        hostSelectedTopics.filter { subTopicPage ->
            selectedSubTopics.contains(subTopicPage.properties["Subtopic"]?.title?.first()?.plainText)
        }.forEach { subTopicPage ->
            val subTopicName = subTopicPage.properties["Subtopic"]?.title?.first()?.plainText
            repeat(countQuestionInSubtopic) {
                val questionsOfSubTopic = retrieveSubTopicQuestions(subTopicPage)
                questions.addRandomQuestion(subTopicPage.topicName, subTopicName, questionsOfSubTopic)
            }
        }

        return TechInterviewDTO(
            questions = questions
        )
    }

    private fun retrieveSubTopicQuestions(subTopic: SubTopicPage): List<Page> {
        return notionClient.retrieveBlockChildren(subTopic.id).results.map { block ->
            if (block.type.value == "child_database") {
                block.id?.let { id ->
                    notionClient.queryDatabase(id).results
                } ?: emptyList()
            } else emptyList()
        }.flatten()
    }

}

private fun MutableList<QuestionDTO>.addRandomQuestion(
    topicName: String?,
    subTopicName: String?,
    questionsOfSubTopic: List<Page>
) {
    val randomQuestion = questionsOfSubTopic.random().toDTO(topicName, subTopicName)
    if (!contains(randomQuestion)) {
        add(randomQuestion)
    }
}

private fun Page.toDTO(topicName: String?, subTopicName: String?): QuestionDTO {
    return QuestionDTO(
        text = properties["Question"]?.title?.first()?.plainText,
        topic = topicName,
        subTopic = subTopicName,
        answer = properties["Answer"]?.richText?.first()?.plainText,
        category = properties["Category"]?.richText?.first()?.plainText
    )
}
