package techinterviewbot.interview.api.host

import kotlinx.coroutines.flow.StateFlow
import techinterviewbot.interview.internal.domain.models.AnswerGrade

public interface InterviewHost {
    public val state: StateFlow<State>
    public fun start(topics: List<String>, subTopics: List<String>)
    public fun nextQuestion()
    public fun prevQuestion()
    public fun rateAnswerGrade(grade: AnswerGrade)
}