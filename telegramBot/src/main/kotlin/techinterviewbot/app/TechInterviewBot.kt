package techinterviewbot.app

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.Chat
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.polls.PollAnswer
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import techinterviewbot.data.source.InterviewSource
import techinterviewbot.data.source.excel.ExcelDataSource
import techinterviewbot.interview.api.host.DurationMode
import techinterviewbot.interview.api.host.InterviewHost
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.State
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener
import techinterviewbot.interview.api.utils.toGrade
import techinterviewbot.interview.internal.domain.InterviewRepository
import techinterviewbot.interview.internal.domain.InterviewRepositoryImpl
import techinterviewbot.interview.internal.domain.models.SubtopicDomain
import techinterviewbot.interview.internal.domain.models.TopicDomain
import techinterviewbot.utilities.StringValues

class TechInterviewBot {

    companion object {
        const val EXCEL_FILE_PATH = "YOUR_PATH"
    }

    // Setup
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val interviewSource: InterviewSource =
        ExcelDataSource(inputStream = File(EXCEL_FILE_PATH).inputStream())
    private var host: InterviewHost =
        InterviewerHostImpl(source = interviewSource, object : TechInterviewEventListener {
            override fun onEvent(event: TechInterviewEvent) {
                when (event) {
                    is TechInterviewEvent.Finish -> bot.sendMessage(
                        chatId = ChatId.fromId(chat.id),
                        text = prettyPrintReport(event)
                    )

                    TechInterviewEvent.Start -> bot.sendMessage(
                        chatId = ChatId.fromId(chat.id),
                        text = StringValues.InterviewStarted.get()
                    )
                }
            }
        })

    private val interviewRepository: InterviewRepository = InterviewRepositoryImpl(interviewSource)

    // Logic
    private var state: InterviewState = InterviewState.Initial
    private lateinit var chat: Chat

    private val bot = bot {
        token = System.getenv("TECH_INTERVIEW_BOT_TOKEN")
        timeout = 30
        logLevel = LogLevel.Network.Body

        dispatch {

            command("start") {
                chat = message.chat
                host.reset()
                val possibleTopics = interviewRepository.getTopics()
                if (possibleTopics.size < 2) {
                    state = InterviewState.TopicsSelected
                    host.possibleTopics = possibleTopics
                    askSubtopics(interviewRepository.getSubTopics().filter { subtopicDomain ->
                        possibleTopics.map { it.id }.contains(subtopicDomain.topicId)
                    })
                } else {
                    askTopics(possibleTopics = possibleTopics)
                }
            }

            pollAnswer {
                when (state) {
                    InterviewState.Initial -> {
                        handleTopicsPollAnswer(pollAnswer)
                        askSubtopics(possibleSubTopics = interviewRepository.getSubTopics().filter { subtopicDomain ->
                            host.possibleTopics?.map { it.id }?.contains(subtopicDomain.topicId) ?: false
                        })
                    }

                    is InterviewState.TopicsSelected -> {
                        handleSubTopicsPollAnswer(pollAnswer)
                        askDurationMode()
                    }

                    is InterviewState.SubTopicsSelected -> {
                        state = InterviewState.Initial
                        handleDurationModePollAnswer(pollAnswer)
                        startQuiz()
                    }
                }
            }

            message(Filter.Text) {
                message.text?.let { text ->
                    host.rateAnswerGrade(text.toGrade())
                    host.nextQuestion()
                }
            }
        }
    }

    // Public API
    fun startPolling() {
        bot.startPolling()
    }

    private fun askTopics(possibleTopics: List<TopicDomain>) {
        bot.sendPoll(
            chatId = ChatId.fromId(chat.id),
            question = StringValues.WhichTopicsNeeded.get(),
            options = possibleTopics.map { it.name },
            isAnonymous = false,
            allowsMultipleAnswers = true
        )
    }

    private fun handleTopicsPollAnswer(pollAnswer: PollAnswer) {
        state = InterviewState.TopicsSelected
        val topics = interviewRepository.getTopics()

        host.possibleTopics = pollAnswer.optionIds.map { option ->
            topics[option]
        }
    }

    private fun askSubtopics(possibleSubTopics: List<SubtopicDomain>) {
        bot.sendPoll(
            chatId = ChatId.fromId(chat.id),
            question = StringValues.WhichTopicsNeeded.get(),
            options = possibleSubTopics.map { it.name },
            isAnonymous = false,
            allowsMultipleAnswers = true
        )
    }

    private fun handleSubTopicsPollAnswer(pollAnswer: PollAnswer) {
        state = InterviewState.SubTopicsSelected

        val subTopics = interviewRepository.getSubTopics().filter { subtopicDomain ->
            host.possibleTopics?.map { it.id }?.contains(subtopicDomain.topicId) ?: false
        }

        host.possibleSubTopics = pollAnswer.optionIds.map { option -> subTopics[option] }
    }

    private fun askDurationMode() {
        bot.sendPoll(
            chatId = ChatId.fromId(chat.id),
            question = StringValues.SelectDurationMode.get(),
            options = DurationMode.values().map { mode ->
                when (mode) {
                    DurationMode.SHORT -> StringValues.ShortInterviewMode.get()
                    DurationMode.MEDIUM -> StringValues.MediumInterviewMode.get()
                    DurationMode.LARGE -> StringValues.LongInterviewMode.get()
                }
            },
            isAnonymous = false,
            allowsMultipleAnswers = false
        )
    }

    private fun handleDurationModePollAnswer(pollAnswer: PollAnswer) {
        host.durationMode = DurationMode.values()[pollAnswer.optionIds.first()]
    }

    private fun startQuiz() {
        scope.coroutineContext.cancelChildren()
        scope.launch {
            host.state.collect { state ->
                if (state != State.Initial) {
                    bot.sendMessage(
                        chatId = ChatId.fromId(chat.id),
                        text = prettyPrintQuestion(state.question),
                        parseMode = ParseMode.HTML,
                        replyMarkup = createInterviewerKeyboard()
                    )
                    bot.sendMessage(
                        chatId = ChatId.fromId(chat.id),
                        text = StringValues.HowYouRateAnswer.get(),
                    )
                }
            }
        }

        bot.sendMessage(
            chatId = ChatId.fromId(chat.id),
            text = StringValues.GenerateInterviewPleaseWait.get(),
        )

        host.start()
    }
}