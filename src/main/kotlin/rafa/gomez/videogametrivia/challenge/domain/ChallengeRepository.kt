package rafa.gomez.videogametrivia.challenge.domain

import arrow.core.raise.Raise
import rafa.gomez.videogametrivia.question.domain.Difficulty

interface ChallengeRepository {
    suspend fun find(criteria: FindChallengeCriteria): Challenge?
    suspend fun search(criteria: SearchChallengeCriteria): List<Challenge>
    suspend fun save(challenge: Challenge)
}

sealed class SearchChallengeCriteria {
    class ByCategory(val category: Category) : SearchChallengeCriteria()
    class ByCategoryAndDifficulty(val category: Category, val difficulty: Difficulty) : SearchChallengeCriteria()
}

sealed class FindChallengeCriteria {
    class ByCategory(val category: Category) : FindChallengeCriteria()
    class ById(val id: ChallengeId) : FindChallengeCriteria()
    class ByCategoryAndDifficulty(val category: Category, val difficulty: Difficulty) : FindChallengeCriteria()
}

context(Raise<Error>)
suspend fun <Error> ChallengeRepository.findOrElse(criteria: FindChallengeCriteria, onError: () -> Error): Challenge =
    find(criteria) ?: raise(onError())
