package techinterviewbot.interview.api.source

import techinterviewbot.interview.internal.domain.models.TechInterview

public interface InterviewSource {
    public fun getTopics(): List<String>
    public fun getSubTopics(selectedTopics: List<String>): List<String>
    public fun generateTechInterview(topics: List<String>, subTopics: List<String>): TechInterview
}