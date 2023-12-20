package rafa.gomez.videogametrivia.challenge.application.solve

import arrow.core.raise.Raise
import rafa.gomez.videogametrivia.challenge.domain.Answer
import rafa.gomez.videogametrivia.challenge.domain.ChallengeId
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.shared.UserId
import rafa.gomez.videogametrivia.shared.event.bus.DomainEventPublisher

class SolveChallengeCommandHandler(
    repository: ChallengeRepository,
    publisher: DomainEventPublisher
) {

    private val solveChallenge = ChallengeSolver(repository, publisher)

    context(Raise<SolveChallengeError>)
    suspend fun handle(command: SolveChallengeCommand) {
        solveChallenge(
            id = ChallengeId.fromString(command.id),
            answers = command.answers.map { Answer(it) },
            userId = UserId.fromString(command.userId)
        )
    }
}

data class SolveChallengeCommand(
    val id: String,
    val answers: List<String>,
    val userId: String
)
