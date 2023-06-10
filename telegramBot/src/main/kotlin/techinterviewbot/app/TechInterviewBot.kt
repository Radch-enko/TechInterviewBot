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
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import techinterviewbot.interview.api.host.InterviewHost
import techinterviewbot.interview.api.host.InterviewerHostImpl
import techinterviewbot.interview.api.host.TechInterviewEvent
import techinterviewbot.interview.api.host.TechInterviewEventListener
import techinterviewbot.interview.api.source.InterviewSource
import techinterviewbot.interview.api.source.MockInterviewSource
import techinterviewbot.interview.api.utils.toGrade
import techinterviewbot.utilities.StringUtil
import techinterviewbot.utilities.StringValues

class TechInterviewBot {

    val scope = CoroutineScope(Dispatchers.Unconfined)
    private var host: InterviewHost? = null
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
                    startQuiz(topics, subTopics)
                }
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

        private fun CommandHandlerEnvironment.startQuiz(topics: List<String>, subTopics: List<String>) {
            scope.launch {
                host?.state?.collect { state ->
                    bot.sendMessage(
                        chatId = ChatId.fromId(update.message!!.chat.id),
                        text = state.question.text,
                        replyMarkup = createInterviewerKeyboard()
                    )
                }
            }

            host?.start(topics, subTopics)

            dispatcher.message(Filter.Text) {
                message.text?.let {
                    host?.rateAnswerGrade(it.toGrade())
                    host?.nextQuestion()
                }
            }
        }

        private fun CommandHandlerEnvironment.askHostAboutTopic(
            onHostAnswer: (selectedTopics: List<String>) -> Unit
        ) {
            val allTopics = interviewSource.getTopics()
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
                                text = StringUtil.getString(StringValues.InterviewStarted)
                            )
                        }
                    }
                }
            )
        }
    }
}