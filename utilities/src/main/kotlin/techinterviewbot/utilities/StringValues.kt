package techinterviewbot.utilities

public class StringValues {

    public object InterviewStarted : StringLocalization {
        override val russian: String
            get() = "Начинаем интервью"
    }

    public object Perfect : StringLocalization {
        override val russian: String
            get() = "Отлично"
    }

    public object Inexperience : StringLocalization {
        override val russian: String
            get() = "Нет опыта"
    }

    public object IncompleteKnowledge : StringLocalization {
        override val russian: String
            get() = "Неглубокие знания"
    }

    public object Other : StringLocalization {
        override val russian: String
            get() = "Оставить текстовую заметку"
    }

    public object InterViewReport : StringLocalization {
        override val russian: String
            get() = "Итоговый отчет по кандидату:"
    }

    public object WhichTopicsNeeded : StringLocalization {
        override val russian: String
            get() = "По каким разделам вы хотите провести интервью?"
    }

    public object WhichSubTopicsNeeded : StringLocalization {
        override val russian: String
            get() = "Выберите темы, которые необходимо затронуть"
    }

    public object SelectDurationMode : StringLocalization {
        override val russian: String
            get() = "Выберите длительность интервью"
    }

    public object ShortInterviewMode : StringLocalization {
        override val russian: String
            get() = "Короткое (3 вопроса по каждой под теме)"
    }

    public object MediumInterviewMode : StringLocalization {
        override val russian: String
            get() = "Среднее (5 вопросов по каждой под теме)"
    }

    public object LongInterviewMode : StringLocalization {
        override val russian: String
            get() = "Большое (10 вопросов по каждой под теме)"
    }

    public object HowYouRateAnswer : StringLocalization {
        override val russian: String
            get() = "Как бы вы оценили ответ кандидата?"
    }

    public object GenerateInterviewPleaseWait : StringLocalization {
        override val russian: String
            get() = "Составляю список вопросов... Пожалуйста подождите"
    }

    public object Question : StringLocalization {
        override val russian: String
            get() = "Вопрос"
    }

    public object Answer : StringLocalization {
        override val russian: String
            get() = "Ответ"
    }

    public object Category : StringLocalization {
        override val russian: String
            get() = "Важность"
    }

    public object Topic : StringLocalization {
        override val russian: String
            get() = "Раздел"
    }

    public object SubTopic : StringLocalization {
        override val russian: String
            get() = "Тема"
    }
}