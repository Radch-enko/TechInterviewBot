package techinterviewbot.data.source.excel

import java.io.InputStream
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import techinterviewbot.data.dto.QuestionDTO
import techinterviewbot.data.dto.SubtopicDTO
import techinterviewbot.data.dto.TechInterviewDTO
import techinterviewbot.data.dto.TopicDTO
import techinterviewbot.data.source.InterviewSource

public class ExcelDataSource(private val inputStream: InputStream) : InterviewSource {

    private val workbook = WorkbookFactory.create(inputStream)

    override fun getTopics(): List<TopicDTO> {
        val workSheet: Sheet = workbook.getSheet("Topics")
        return workSheet.filterIndexed { index, _ -> index != 0 }.mapIndexed { _, row ->
            val indexCell = row.getCell(0)
            val nameCell = row.getCell(1)

            TopicDTO(
                id = indexCell.numericCellValue.toInt(),
                name = nameCell.stringCellValue.toString()
            )
        }
    }

    override fun getSubTopics(): List<SubtopicDTO> {
        return getSubTopicsRows().map { row ->
            SubtopicDTO(
                id = row.getCell(0).numericCellValue.toInt(),
                name = row.getCell(1).stringCellValue,
                topicId = row.getCell(2).numericCellValue.toInt()
            )
        }
    }

    override fun generateTechInterview(
        countQuestionInSubtopic: Int,
        selectedSubTopics: List<SubtopicDTO>
    ): TechInterviewDTO {
        val workSheet: Sheet = workbook.getSheet("Questions")
        val questions = workSheet.filterIndexed { index, _ ->
            index != 0
        }.filter { row ->
            val indexCell = row.getCell(0)
            val questionCell = row.getCell(1)
            val answerCell = row.getCell(2)
            val importanceCell = row.getCell(3)
            val topicIdCell = row.getCell(4)
            val subTopicIdCell = row.getCell(5)

            indexCell != null &&
                    questionCell != null &&
                    answerCell != null &&
                    importanceCell != null &&
                    topicIdCell != null &&
                    subTopicIdCell != null
        }
            .map { row ->
                val indexCell = row.getCell(0)
                val questionCell = row.getCell(1)
                val answerCell = row.getCell(2)
                val importanceCell = row.getCell(3)
                val topicIdCell = row.getCell(4)
                val subTopicIdCell = row.getCell(5)

                QuestionDTO(
                    id = indexCell.numericCellValue.toInt(),
                    text = questionCell.stringCellValue,
                    answer = answerCell.stringCellValue,
                    topic = getTopic(topicIdCell.numericCellValue.toInt()),
                    subTopic = getSubTopic(subTopicIdCell.numericCellValue.toInt()),
                    category = importanceCell.stringCellValue
                )
            }.filter { question ->
                selectedSubTopics.map { it.id }.contains(question.subTopic?.id)
            }

        val randomQuestions = mutableListOf<QuestionDTO>()

        val groupedQuestions = questions.groupBy { it.subTopic }
        groupedQuestions.forEach { entry ->
            repeat(countQuestionInSubtopic) {
                randomQuestions.add(entry.value.random())
            }
        }

        return TechInterviewDTO(
            questions = randomQuestions
        )
    }

    internal fun getSubTopicsRows(): List<Row> {
        val workSheet: Sheet = workbook.getSheet("Subtopics")

        return workSheet
            .filterIndexed { index, _ -> index != 0 }
            .filter { row ->
                val indexCell = row.getCell(0)
                val nameCell = row.getCell(1)
                indexCell != null && nameCell != null
            }
    }

    internal fun getSubTopic(id: Int): SubtopicDTO? {
        val row = getSubTopicsRows().find { row ->
            row.getCell(0).numericCellValue.toInt() == id
        }
        return row?.let {
            SubtopicDTO(
                id = row.getCell(0).numericCellValue.toInt(),
                name = row.getCell(1).stringCellValue,
                topicId = row.getCell(2).numericCellValue.toInt()
            )
        }
    }

    private fun getTopic(id: Int): TopicDTO? {
        return getTopics().find { it.id == id }
    }

}