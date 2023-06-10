package techinterviewbot.interview.api.utils

import techinterviewbot.interview.internal.domain.models.AnswerGrade
import techinterviewbot.utilities.StringUtil
import techinterviewbot.utilities.StringValues

public fun String.toGrade(): AnswerGrade {
    return when (this) {
        StringUtil.getString(StringValues.Perfect) -> AnswerGrade.GoodAnswer
        StringUtil.getString(StringValues.Inexperience) -> AnswerGrade.NoExperience
        StringUtil.getString(StringValues.IncompleteKnowledge) -> AnswerGrade.IncompleteKnowledge
        else -> AnswerGrade.Note(this)
    }
}