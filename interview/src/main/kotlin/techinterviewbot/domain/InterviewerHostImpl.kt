package techinterviewbot.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import techinterviewbot.domain.models.AnswerGrade
import techinterviewbot.domain.models.Question
import techinterviewbot.domain.models.TechInterview

public class InterviewerHostImpl(
    private val techInterview: TechInterview,
    private val listener: TechInterviewEventListener
) : InterviewHost {

    private val _interviewState = MutableStateFlow(State(index = -1, question = Question("", "", "")))
    public override val state: StateFlow<State> = _interviewState.asStateFlow()
    private var isInterviewStarted: Boolean = false

    override fun start() {
        isInterviewStarted = true
        listener.onEvent(TechInterviewEvent.Start)
        _interviewState.value = State(index = 0, question = techInterview.questions.first())
    }

    override fun nextQuestion() {
        val currentIndex = _interviewState.value.index
        if (currentIndex < techInterview.questions.lastIndex) {
            val nextIndex = _interviewState.value.index + 1
            val nextQuestion = techInterview.questions[nextIndex]
            _interviewState.value = State(index = nextIndex, question = nextQuestion)
        } else {
            listener.onEvent(TechInterviewEvent.Finish(techInterview.generateReport()))
        }
    }

    override fun prevQuestion() {
        if (_interviewState.value.index > 0) {
            val nextIndex = _interviewState.value.index - 1
            val nextQuestion = techInterview.questions[nextIndex]
            _interviewState.update {
                it.copy(index = nextIndex, question = nextQuestion)
            }
        }
    }

    override fun rateAnswerGrade(grade: AnswerGrade) {
        if (isInterviewStarted) {
            _interviewState.value.question.answerGrade = grade
        }
    }

    internal fun navigateToQuestion(index: Int) {
        val nextQuestion = techInterview.questions[index]
        _interviewState.value = State(index = index, question = nextQuestion)
    }


}