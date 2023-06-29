package techinterviewbot.interview.api.host

import techinterviewbot.interview.internal.domain.models.Question
import techinterviewbot.interview.internal.domain.models.SubtopicDomain
import techinterviewbot.interview.internal.domain.models.TopicDomain

public data class State(
    val index: Int,
    val question: Question
) {
    public companion object {
        public val Initial: State = State(
            -1, question = Question(
                "",
                TopicDomain(
                    -1, ""
                ),
                SubtopicDomain(
                    -1,
                    "",
                    -1
                ),
                ""
            )
        )
    }
}