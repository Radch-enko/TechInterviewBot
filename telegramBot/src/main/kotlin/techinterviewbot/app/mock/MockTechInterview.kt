package techinterviewbot.app.mock

import techinterviewbot.domain.models.Question
import techinterviewbot.domain.models.TechInterview

object MockTechInterview {

    fun generate(): TechInterview {
        return TechInterview(
            questions = listOf(
                Question("What is an algorithm?", "Algorithms", "Necessary to know"),
                Question("What is the difference between TCP and UDP?", "Networking", "Necessary to know"),
                Question("What is a primary key in a database?", "Databases", "Necessary to know"),
                Question(
                    "What is the time complexity of quicksort algorithm?",
                    "Algorithms",
                    "Necessary to know"
                ),
                Question(
                    "What is the purpose of an index in a database?",
                    "Databases",
                    "Desirable to know"
                ),
                Question(
                    "What is the OSI model in computer networking?",
                    "Networking",
                    "Desirable to know"
                ),
                Question("What is a foreign key in a database?", "Databases", "Necessary to know"),
                Question(
                    "What is the difference between a stack and a queue?",
                    "Data Structures",
                    "Necessary to know"
                ),
                Question(
                    "What is the role of DNS in computer networks?",
                    "Networking",
                    "Necessary to know"
                ),
                Question(
                    "What is the ACID property in database transactions?",
                    "Databases",
                    "Desirable to know"
                )
            )
        )
    }
}