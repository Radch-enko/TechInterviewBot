package techinterviewbot.utilities

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class StringUtilTest {

    @Test
    fun `Test Russian localization`() {
        val stringUtil = StringUtil(Language.RUSSIAN)

        val actual = stringUtil.getString(StringValues.InterviewStarted)
        val expected = "Начинаем интервью"
        assertEquals(expected, actual)
    }
}