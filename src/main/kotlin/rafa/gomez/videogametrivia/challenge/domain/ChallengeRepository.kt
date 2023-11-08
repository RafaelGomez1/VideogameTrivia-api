package rafa.gomez.videogametrivia.challenge.domain

import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.QuestionId

interface ChallengeRepository {

    suspend fun find(criteria: FindChallengeCriteria): Challenge?
    suspend fun search(criteria: SearchChallengeCriteria): List<Challenge>
    suspend fun save(challenge: Challenge)
}

sealed class SearchChallengeCriteria {
    class ById(val id: QuestionId): SearchChallengeCriteria()
}

sealed class FindChallengeCriteria {
    class ByCategory(val category: Category): FindChallengeCriteria()
    class ByCategoryAndDifficulty(val category: Category, val difficulty: Difficulty): FindChallengeCriteria()
}
