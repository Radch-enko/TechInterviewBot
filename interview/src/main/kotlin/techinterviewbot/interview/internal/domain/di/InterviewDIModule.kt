package techinterviewbot.interview.internal.domain.di

import org.koin.core.module.Module
import org.koin.dsl.module
import techinterviewbot.interview.internal.domain.InterviewQuestionsRepository
import techinterviewbot.interview.internal.domain.InterviewTopicsRepository
import techinterviewbot.interview.internal.domain.implementation.InterviewQuestionsRepositoryImpl
import techinterviewbot.interview.internal.domain.implementation.InterviewTopicsRepositoryImpl

public val interviewDIModule: Module = module {
    single<InterviewTopicsRepository> { InterviewTopicsRepositoryImpl() }
    single<InterviewQuestionsRepository> { InterviewQuestionsRepositoryImpl() }
}