package techinterviewbot.data.source

import techinterviewbot.data.dto.SubtopicDTO
import techinterviewbot.data.dto.TechInterviewDTO
import techinterviewbot.data.dto.TopicDTO

public interface InterviewSource {
    public fun getTopics(): List<TopicDTO>
    public fun getSubTopics(): List<SubtopicDTO>
    public fun generateTechInterview(
        countQuestionInSubtopic: Int,
        selectedSubTopics: List<SubtopicDTO>
    ): TechInterviewDTO
}