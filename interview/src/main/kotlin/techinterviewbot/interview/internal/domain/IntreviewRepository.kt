package techinterviewbot.interview.internal.domain

import techinterviewbot.interview.internal.domain.models.SubtopicDomain
import techinterviewbot.interview.internal.domain.models.TechInterview
import techinterviewbot.interview.internal.domain.models.TopicDomain

public interface InterviewRepository {

    public fun getTopics(): List<TopicDomain>
    public fun getSubTopics(): List<SubtopicDomain>
    public fun generateTechInterview(
        countQuestionInSubtopic: Int,
        selectedSubTopics: List<SubtopicDomain>
    ): TechInterview
}