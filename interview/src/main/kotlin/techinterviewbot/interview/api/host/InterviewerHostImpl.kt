package techinterviewbot.interview.api.host

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import techinterviewbot.data.source.InterviewSource
import techinterviewbot.interview.internal.domain.converter.toDTO
import techinterviewbot.interview.internal.domain.converter.toDomain
import techinterviewbot.interview.internal.domain.models.AnswerGrade
import techinterviewbot.interview.internal.domain.models.SubtopicDomain
import techinterviewbot.interview.internal.domain.models.TechInterview
import techinterviewbot.interview.internal.domain.models.TopicDomain

public class InterviewerHostImpl(
    private val source: InterviewSource,
    private val listener: TechInterviewEventListener,
) : InterviewHost, KoinComponent {

    internal lateinit var techInterview: TechInterview
    private val _interviewState: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    public override val state: StateFlow<State> = _interviewState.asStateFlow()

    private var isInterviewStarted: Boolean = false

    public override var possibleTopics: List<TopicDomain>? = null
    public override var possibleSubTopics: List<SubtopicDomain>? = null
    public override var durationMode: DurationMode? = null

    override fun start() {
        if (durationMode != null && possibleSubTopics != null) {
            possibleSubTopics!!.toDTO().forEach { dto ->
                println(dto)
            }
            techInterview =
                source.generateTechInterview(
                    durationMode!!.countQuestionInSubTopic,
                    selectedSubTopics = possibleSubTopics!!.toDTO()
                ).toDomain()
            isInterviewStarted = true
            listener.onEvent(TechInterviewEvent.Start)
            _interviewState.value = State(index = 0, question = techInterview.questions.first())
        }
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

    override fun reset() {
        this.durationMode = null
        this.possibleTopics = null
        this.possibleSubTopics = null
        _interviewState.value = State.Initial
    }

    internal fun navigateToQuestion(index: Int) {
        val nextQuestion = techInterview.questions[index]
        _interviewState.value = State(index = index, question = nextQuestion)
    }


}