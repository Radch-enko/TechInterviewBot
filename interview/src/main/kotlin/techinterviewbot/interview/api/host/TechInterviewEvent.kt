package techinterviewbot.interview.api.host

import techinterviewbot.interview.internal.domain.models.TechInterviewReport

public sealed class TechInterviewEvent {
    public object Start : TechInterviewEvent()
    public data class Finish(val report: TechInterviewReport) : TechInterviewEvent()
}