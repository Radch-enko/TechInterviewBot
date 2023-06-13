package techinterviewbot.interview.api.host

import techinterviewbot.interview.internal.domain.models.Question

public data class State(
    val index: Int,
    val question: Question
) {
    public companion object {
        public val Initial: State = State(-1, question = Question("", "", "", ""))
    }
}