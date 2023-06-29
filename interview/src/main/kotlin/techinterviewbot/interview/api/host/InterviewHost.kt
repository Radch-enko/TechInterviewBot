package techinterviewbot.interview.api.host

import kotlinx.coroutines.flow.StateFlow
import techinterviewbot.interview.internal.domain.models.AnswerGrade
import techinterviewbot.interview.internal.domain.models.SubtopicDomain
import techinterviewbot.interview.internal.domain.models.TopicDomain

public interface InterviewHost {
    public val state: StateFlow<State>
    public var possibleTopics: List<TopicDomain>?
    public var possibleSubTopics: List<SubtopicDomain>?
    public var durationMode: DurationMode?
    public fun start()
    public fun nextQuestion()
    public fun prevQuestion()
    public fun reset()
    public fun rateAnswerGrade(grade: AnswerGrade)
}