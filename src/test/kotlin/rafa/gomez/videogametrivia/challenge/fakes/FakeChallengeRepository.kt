package rafa.gomez.videogametrivia.challenge.fakes

import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.ChallengeId
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria.ByCategory
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria.ByCategoryAndDifficulty
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria.ByCategory as SearchByCategory
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria.ByCategoryAndDifficulty as SearchByCategoryAndDifficulty
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria.ById
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria
import rafa.gomez.videogametrivia.shared.fakes.FakeRepository

object FakeChallengeRepository: ChallengeRepository, FakeRepository<ChallengeId, Challenge> {
    override val elements = mutableMapOf<ChallengeId, Challenge>()
    override val errors = mutableListOf<Throwable>()

    override suspend fun find(criteria: FindChallengeCriteria): Challenge? =
        when(criteria) {
            is ByCategory -> elements.values.firstOrNull { it.category == criteria.category }
            is ByCategoryAndDifficulty -> elements.values.firstOrNull { it.category == criteria.category && it.difficulty == criteria.difficulty}
            is ById -> elements[criteria.id]
        }

    override suspend fun search(criteria: SearchChallengeCriteria): List<Challenge> =
        when(criteria) {
            is SearchByCategory -> elements.values.filter { it.category == criteria.category }
            is SearchByCategoryAndDifficulty -> elements.values.filter { it.category == criteria.category && it.difficulty == criteria.difficulty}
        }

    override suspend fun save(challenge: Challenge) {
        failIfConfiguredOrElse { elements.saveOrUpdate(challenge.id, challenge) }
    }

}
