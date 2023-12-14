package rafa.gomez.videogametrivia.challenge.application.solve

import arrow.core.raise.Raise
import arrow.core.raise.withError
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeError.ChallengeFailed
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeError.NotFound
import rafa.gomez.videogametrivia.challenge.domain.Answer
import rafa.gomez.videogametrivia.challenge.domain.ChallengeId
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria.ById
import rafa.gomez.videogametrivia.challenge.domain.findOrElse
import rafa.gomez.videogametrivia.shared.UserId
import rafa.gomez.videogametrivia.shared.event.bus.DomainEventPublisher

class ChallengeSolver(
    private val repository: ChallengeRepository,
    private val publisher: DomainEventPublisher
) {

    context(Raise<SolveChallengeError>)
    suspend operator fun invoke(id: ChallengeId, answers: List<Answer>, userId: UserId) {
        val challenge = repository.findOrElse(ById(id)) { NotFound }

        val solvedChallenge = withError({ ChallengeFailed }) { challenge.solve(answers, userId) }

        publisher.publish(solvedChallenge.pullEvents())
    }
}

sealed class SolveChallengeError {
    object NotFound : SolveChallengeError()
    object ChallengeFailed : SolveChallengeError()
}
