package techinterviewbot.domain

import kotlinx.coroutines.flow.StateFlow
import techinterviewbot.domain.models.AnswerGrade

public interface InterviewHost {
    public val state: StateFlow<State>
    public fun start()
    public fun nextQuestion()
    public fun prevQuestion()
    public fun rateAnswerGrade(grade: AnswerGrade)
}