package techinterviewbot.app

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.HandleCommand
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import techinterviewbot.data.source.InterviewSource
import techinterviewbot.data.source.mock.MockInterviewSource
import techinterviewbot.interview.api.host.DurationMode
import techinterviewbot.interview.api.host.InterviewHost
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.State
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener
import techinterviewbot.interview.api.utils.toGrade
import techinterviewbot.utilities.StringValues

class TechInterviewBot {

    val scope = CoroutineScope(Dispatchers.Unconfined)
    private var host: InterviewHost? = null

    //    private val interviewSource: InterviewSource = NotionDataSource(
//        token = System.getenv("TECH_INTERVIEW_CONNECTION_TOKEN"),
//        databaseId = System.getenv("TECH_INTERVIEW_DATABASE_ID")
//    )
    private val interviewSource: InterviewSource = MockInterviewSource()

    private val bot = bot {
        token = System.getenv("TECH_INTERVIEW_BOT_TOKEN")
        timeout = 30
        logLevel = LogLevel.Network.Body
        dispatch {
            command("start", StartInterviewCommand(this))
        }
    }

    fun startPooling() {
        bot.startPolling()
    }

    private inner class StartInterviewCommand(val dispatcher: Dispatcher) : HandleCommand {
        override suspend fun invoke(environment: CommandHandlerEnvironment) = with(environment) {
            createInterviewer()
            askHostAboutTopic { topics ->
                askHostAboutSubTopic(selectedTopics = topics) { subTopics ->
                    askHostAboutDurationMode { mode ->
                        startQuiz(subTopics, mode)
                    }
                }
            }
        }

        private fun CommandHandlerEnvironment.createInterviewer() {
            host = InterviewerHostImpl(
                source = interviewSource,
                listener = object : TechInterviewEventListener {
                    override fun onEvent(event: TechInterviewEvent) {
                        when (event) {
                            is TechInterviewEvent.Finish -> bot.sendMessage(
                                chatId = ChatId.fromId(update.message!!.chat.id),
                                text = prettyPrintReport(event)
                            )

                            TechInterviewEvent.Start -> bot.sendMessage(
                                chatId = ChatId.fromId(update.message!!.chat.id),
                                text = StringValues.InterviewStarted.get()
                            )
                        }
                    }
                }
            )
        }

        private fun CommandHandlerEnvironment.askHostAboutTopic(
            onHostAnswer: (selectedTopics: List<String>) -> Unit
        ) {
            val allTopics = interviewSource.getTopics()
            if (allTopics.size > 1) {
                var isAnswered = false
                dispatcher.pollAnswer {
                    if (!isAnswered) {
                        onHostAnswer(pollAnswer.optionIds.map { ids ->
                            allTopics[ids]
                        })
                        isAnswered = true
                    }
                }

                bot.sendPoll(
                    chatId = ChatId.fromId(message.chat.id),
                    question = StringValues.WhichTopicsNeeded.get(),
                    options = allTopics,
                    isAnonymous = false,
                    allowsMultipleAnswers = true
                )
            } else {
                onHostAnswer(listOf(allTopics[0]))
            }
        }

        private fun CommandHandlerEnvironment.askHostAboutSubTopic(
            selectedTopics: List<String>,
            onHostAnswer: (selectedSubTopics: List<String>) -> Unit
        ) {
            val allSubTopics = interviewSource.getSubTopics(selectedTopics)
            var isAnswered = false
            dispatcher.pollAnswer {
                if (!isAnswered) {
                    onHostAnswer(pollAnswer.optionIds.map { ids ->
                        allSubTopics[ids]
                    })
                    isAnswered = true
                }
            }

            bot.sendPoll(
                chatId = ChatId.fromId(message.chat.id),
                question = StringValues.WhichSubTopicsNeeded.get(),
                options = allSubTopics,
                isAnonymous = false,
                allowsMultipleAnswers = true
            )
        }

        private fun CommandHandlerEnvironment.askHostAboutDurationMode(onHostAnswer: (durationMode: DurationMode) -> Unit) {
            var isAnswered = false
            dispatcher.pollAnswer {
                if (!isAnswered) {
                    onHostAnswer(DurationMode.values()[pollAnswer.optionIds.first()])
                    isAnswered = true
                }
            }

            bot.sendPoll(
                chatId = ChatId.fromId(message.chat.id),
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

        private fun CommandHandlerEnvironment.startQuiz(
            subTopics: List<String>,
            mode: DurationMode
        ) {
            scope.launch {
                host?.state?.collect { state ->
                    if (state != State.Initial) {
                        bot.sendMessage(
                            chatId = ChatId.fromId(update.message!!.chat.id),
                            text = prettyPrintQuestion(state.question),
                            parseMode = ParseMode.HTML,
                            replyMarkup = createInterviewerKeyboard()
                        )
                        bot.sendMessage(
                            chatId = ChatId.fromId(update.message!!.chat.id),
                            text = StringValues.HowYouRateAnswer.get(),
                        )
                    }
                }
            }

            bot.sendMessage(
                chatId = ChatId.fromId(update.message!!.chat.id),
                text = StringValues.GenerateInterviewPleaseWait.get(),
            )
//            bot.sendAnimation(
//                chatId = ChatId.fromId(update.message!!.chat.id),
//                animation = TelegramFile.ByFile(File("src/main/resources/LoadingAnimation.tgs"))
//            )
            host?.start(subTopics, mode)

            dispatcher.message(Filter.Text) {
                message.text?.let { text ->
                    host?.rateAnswerGrade(text.toGrade())
                    host?.nextQuestion()
                }
            }
        }
    }
}