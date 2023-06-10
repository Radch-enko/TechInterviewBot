package techinterviewbot.domain.models

/**
 * Interview step
 * @param text is a text of question
 * @param topic one of the sections of Computer scince that a programmer should know. For example: "alogrithms", "network", "database" etc.
 * @param category is complex of question. Can be `Necessary to know` or `Desirable to know`
 */
public data class Question(
    val text: String,
    val topic: String,
    val category: String,
) {
    public var answerGrade: AnswerGrade? = null
        internal set
}
