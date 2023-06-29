package techinterviewbot.data.dto

public data class QuestionDTO(
    val id: Int,
    val text: String,
    val topic: TopicDTO?,
    val subTopic: SubtopicDTO?,
    val answer: String?,
    val category: String?,
)
