package techinterviewbot.app

import techinterviewbot.interview.api.host.DurationMode

data class StartQuizParams(
    val subTopics: List<String>? = null,
    val mode: DurationMode? = null
)
