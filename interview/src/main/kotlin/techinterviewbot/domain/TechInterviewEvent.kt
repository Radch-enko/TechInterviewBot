package techinterviewbot.domain

import techinterviewbot.domain.models.TechInterviewReport

public sealed class TechInterviewEvent {
    public object Start : TechInterviewEvent()
    public data class Finish(val report: TechInterviewReport) : TechInterviewEvent()
}