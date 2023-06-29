package techinterviewbot.data.excel

import java.io.File
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import techinterviewbot.data.dto.SubtopicDTO
import techinterviewbot.data.dto.TopicDTO
import techinterviewbot.data.source.excel.ExcelDataSource

class ExcelDataSourceTest {

    companion object {
        const val EXCEL_FILE_PATH =
            "/Users/stanislav.radchenko/Desktop/PetProjects/TechInterviewBot/telegramBot/src/main/resources/source.xlsx"
    }

    @Test
    fun getTopics() {
        val excelDataSource = ExcelDataSource(File(EXCEL_FILE_PATH).inputStream())
        val expect = listOf<TopicDTO>(TopicDTO(1, "Computer Science"), TopicDTO(2, "Test ( don't select)!"))

        val actual: List<TopicDTO> = excelDataSource.getTopics()
        assertEquals(expect, actual)
    }

    @Test
    fun getSubTopics() {
        val excelDataSource = ExcelDataSource(File(EXCEL_FILE_PATH).inputStream())
        val expect = listOf<SubtopicDTO>(
            SubtopicDTO(id = 1, "Структура и интерпретация компьютерных программ."),
            SubtopicDTO(id = 2, "Архитектура компьютера"),
            SubtopicDTO(id = 3, "Алгоритмы и структуры данных"),
            SubtopicDTO(id = 4, "Математика для CS"),
            SubtopicDTO(id = 5, "Операционные системы"),
            SubtopicDTO(id = 6, "Компьютерные сети"),
            SubtopicDTO(id = 7, "Языки и компиляторы"),
            SubtopicDTO(id = 8, "Распределенные системы"),
            SubtopicDTO(id = 9, "Базы данных"),
        )

        val actual: List<SubtopicDTO> = excelDataSource.getSubTopics()
        assertEquals(expect, actual)
    }

    @Test
    fun getSubTopicById() {
        val excelDataSource = ExcelDataSource(File(EXCEL_FILE_PATH).inputStream())
        val expect = SubtopicDTO(id = 3, "Алгоритмы и структуры данных")
        val actual: SubtopicDTO? = excelDataSource.getSubTopic(3)
        assertEquals(expect, actual)
    }

    @Test
    fun getGeneratedTechInterview() {
        val expectedSize = 15
        val excelDataSource = ExcelDataSource(File(EXCEL_FILE_PATH).inputStream())
        val techInterview = excelDataSource.generateTechInterview(
            countQuestionInSubtopic = 5,
            selectedSubTopics = listOf(
                SubtopicDTO(id = 1, "Структура и интерпретация компьютерных программ."),
                SubtopicDTO(id = 2, "Архитектура компьютера"),
                SubtopicDTO(id = 3, "Алгоритмы и структуры данных"),
            )
        )
        println(techInterview.questions.toString())
        assertEquals(expectedSize, techInterview.questions.size)
    }
}