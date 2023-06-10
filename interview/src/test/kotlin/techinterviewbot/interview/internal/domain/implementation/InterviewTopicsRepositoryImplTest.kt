package techinterviewbot.interview.internal.domain.implementation

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class InterviewTopicsRepositoryImplTest {

    @Test
    fun `Filter "subTopics" by "topic"`() {
        val interviewTopicsRepositoryImpl = InterviewTopicsRepositoryImpl()
        val expect = listOf("Algorithms", "Network", "Database")
        val actual = interviewTopicsRepositoryImpl.getSubTopics(listOf("Computer science"))
        assertEquals(expect, actual)
    }
}