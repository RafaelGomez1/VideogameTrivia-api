package rafa.gomez.videogametrivia.challenge.domain

import rafa.gomez.videogametrivia.question.domain.Difficulty

interface ChallengeRepository {
    suspend fun find(criteria: ChallengeCriteria): Challenge?
    suspend fun search(criteria: ChallengeCriteria): List<Challenge>
    suspend fun save(challenge: Challenge)
}

sealed class ChallengeCriteria {
    class ByCategory(val category: Category): ChallengeCriteria()
    class ByCategoryAndDifficulty(val category: Category, val difficulty: Difficulty): ChallengeCriteria()
}
