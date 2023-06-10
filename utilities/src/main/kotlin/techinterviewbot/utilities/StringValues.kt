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
            get() = "По каким темам вы хотите провести интервью?"
    }

    public object WhichSubTopicsNeeded : StringLocalization {
        override val russian: String
            get() = "Выберите темы, которые необходимо затронуть"
    }
}