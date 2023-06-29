package techinterviewbot.app

sealed class InterviewState {
    object Initial : InterviewState()
    object TopicsSelected : InterviewState()
    object SubTopicsSelected : InterviewState()
}