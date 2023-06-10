package techinterviewbot.interview.internal.domain.models

public sealed class AnswerGrade {
    public object GoodAnswer : AnswerGrade()
    public object IncompleteKnowledge : AnswerGrade()
    public object NoExperience : AnswerGrade()
    public data class Note(val text: String) : AnswerGrade()
}
