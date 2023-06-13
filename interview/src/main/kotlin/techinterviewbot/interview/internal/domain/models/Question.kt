package techinterviewbot.interview.internal.domain.models

/**
 * Interview step
 * @param text is a text of question
 * @param topic one of the sections of Computer scince that a programmer should know. For example: "alogrithms", "network", "database" etc.
 * @param subTopic is subsection of topic
 * @param text answer for question
 * @param category is complex of question. Can be `Necessary to know` or `Desirable to know`
 */
public data class Question(
    val text: String,
    val topic: String,
    val subTopic: String,
    val answer: String?,
    val category: String? = null,
) {
    public var answerGrade: AnswerGrade? = null
        internal set
}
