package techinterviewbot.interview.api.host

import kotlinx.coroutines.flow.StateFlow
import techinterviewbot.interview.internal.domain.models.AnswerGrade

public interface InterviewHost {
    public val state: StateFlow<State>
    public fun start(subTopics: List<String>, durationMode: DurationMode)
    public fun nextQuestion()
    public fun prevQuestion()
    public fun rateAnswerGrade(grade: AnswerGrade)
}