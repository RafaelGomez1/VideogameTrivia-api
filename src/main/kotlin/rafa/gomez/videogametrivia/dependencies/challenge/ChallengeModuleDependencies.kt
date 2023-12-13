package rafa.gomez.videogametrivia.dependencies.challenge

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rafa.gomez.videogametrivia.challenge.application.create.CreateChallengesCommandHandler
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeQueryHandler
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeCommandHandler
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.challenge.secondaryadapter.database.JpaChallengeRepository
import rafa.gomez.videogametrivia.challenge.secondaryadapter.database.MongoChallengeRepository
import rafa.gomez.videogametrivia.question.domain.QuestionRepository
import rafa.gomez.videogametrivia.shared.event.bus.DomainEventPublisher

@Configuration
class ChallengeModuleDependencies {

    @Bean fun challengeRepository(repository: JpaChallengeRepository): ChallengeRepository = MongoChallengeRepository(repository)

    @Bean
    fun solveChallengeCommandHandler(challengeRepository: ChallengeRepository, publisher: DomainEventPublisher): SolveChallengeCommandHandler =
        SolveChallengeCommandHandler(challengeRepository, publisher)

    @Bean
    fun createChallengesCommandHandler(challengeRepository: ChallengeRepository, questionRepository: QuestionRepository): CreateChallengesCommandHandler =
        CreateChallengesCommandHandler(challengeRepository, questionRepository)

    @Bean
    fun searchChallengeQueryHandler(challengeRepository: ChallengeRepository): SearchChallengeQueryHandler =
        SearchChallengeQueryHandler(challengeRepository)
}
