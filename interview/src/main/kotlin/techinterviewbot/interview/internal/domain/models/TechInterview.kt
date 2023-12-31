package techinterviewbot.interview.internal.domain.models

public data class TechInterview(
    val questions: List<Question>,
) {
    public fun generateReport(): TechInterviewReport {
        return TechInterviewReport(data = questions)
    }
}
