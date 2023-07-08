package techinterviewbot.data.excel

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import techinterviewbot.data.dto.SubtopicDTO
import techinterviewbot.data.dto.TopicDTO
import techinterviewbot.data.source.excel.ExcelDataSource

class ExcelDataSourceTest {

    companion object {
        const val EXCEL_FILE_PATH =
            "source.xlsx"
    }

    private val sourceFile = Thread.currentThread().contextClassLoader.getResourceAsStream(EXCEL_FILE_PATH)

    @Test
    fun getTopics() {
        val excelDataSource = ExcelDataSource(sourceFile)
        val expect = listOf<TopicDTO>(TopicDTO(1, "Computer Science"))

        val actual: List<TopicDTO> = excelDataSource.getTopics()
        assertEquals(expect, actual)
    }

    @Test
    fun getSubTopics() {
        val excelDataSource = ExcelDataSource(sourceFile)
        val expect = listOf<SubtopicDTO>(
            SubtopicDTO(id = 1, "Структура и интерпретация компьютерных программ.", 1),
            SubtopicDTO(id = 2, "Архитектура компьютера", 1),
            SubtopicDTO(id = 3, "Алгоритмы и структуры данных", 1),
            SubtopicDTO(id = 4, "Математика для CS", 1),
            SubtopicDTO(id = 5, "Операционные системы", 1),
            SubtopicDTO(id = 6, "Компьютерные сети", 1),
            SubtopicDTO(id = 7, "Языки и компиляторы", 1),
            SubtopicDTO(id = 8, "Распределенные системы", 1),
            SubtopicDTO(id = 9, "Базы данных", 1),
        )

        val actual: List<SubtopicDTO> = excelDataSource.getSubTopics()
        assertEquals(expect, actual)
    }

    @Test
    fun getSubTopicById() {
        val excelDataSource = ExcelDataSource(sourceFile)
        val expect = SubtopicDTO(id = 3, "Алгоритмы и структуры данных", 1)
        val actual: SubtopicDTO? = excelDataSource.getSubTopic(3)
        assertEquals(expect, actual)
    }

    @Test
    fun getGeneratedTechInterview() {
        val expectedSize = 15
        val excelDataSource = ExcelDataSource(sourceFile)
        val techInterview = excelDataSource.generateTechInterview(
            countQuestionInSubtopic = 5,
            selectedSubTopics = listOf(
                SubtopicDTO(id = 1, "Структура и интерпретация компьютерных программ.", 1),
                SubtopicDTO(id = 2, "Архитектура компьютера", 1),
                SubtopicDTO(id = 3, "Алгоритмы и структуры данных", 1),
            )
        )
        println(techInterview.questions.toString())
        assertEquals(expectedSize, techInterview.questions.size)
    }
}