package techinterviewbot.app

import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import techinterviewbot.domain.QuestionRate
import techinterviewbot.domain.TechInterviewEvent
import techinterviewbot.domain.models.AnswerGrade
import techinterviewbot.utilities.StringUtil
import techinterviewbot.utilities.StringValues

fun createInterviewerKeyboard(): KeyboardReplyMarkup {
    val buttons = QuestionRate.values().map { rate ->
        KeyboardButton(
            text = when (rate) {
                QuestionRate.Perfect -> StringUtil.getString(StringValues.Perfect)
                QuestionRate.Inexperience -> StringUtil.getString(StringValues.Inexperience)
                QuestionRate.IncompleteKnowledge -> StringUtil.getString(StringValues.IncompleteKnowledge)
            }
        )
    }.toTypedArray()
    return KeyboardReplyMarkup(
        *buttons, resizeKeyboard = true
    )
}

fun prettyPrintReport(event: TechInterviewEvent.Finish): String {

    val answerStatistic = event.report.data.mapIndexed { index, question ->
        "${index + 1}. ${question.text} - ${
            when (val selectedGrade = question.answerGrade) {
                is AnswerGrade.Note -> selectedGrade.text
                AnswerGrade.GoodAnswer -> StringUtil.getString(StringValues.Perfect)
                AnswerGrade.IncompleteKnowledge -> StringUtil.getString(StringValues.IncompleteKnowledge)
                AnswerGrade.NoExperience -> StringUtil.getString(StringValues.Inexperience)
                else -> ""
            }
        }\n"
    }

    return StringUtil.getString(StringValues.InterViewReport)
        .plus("\n")
        .plus("---------------------------\n")
        .plus(answerStatistic.joinToString(""))
        .plus("---------------------------")
}
