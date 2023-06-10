package techinterviewbot.interview.api.host

import techinterviewbot.interview.internal.domain.models.Question

public data class State(
    val index: Int,
    val question: Question
)