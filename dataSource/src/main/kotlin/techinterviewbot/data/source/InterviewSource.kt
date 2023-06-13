package techinterviewbot.data.source

import techinterviewbot.data.dto.TechInterviewDTO

public interface InterviewSource {
    public fun getTopics(): List<String>
    public fun getSubTopics(selectedTopics: List<String>): List<String>
    public fun generateTechInterview(
        countQuestionInSubtopic: Int,
        selectedSubTopics: List<String>
    ): TechInterviewDTO
}