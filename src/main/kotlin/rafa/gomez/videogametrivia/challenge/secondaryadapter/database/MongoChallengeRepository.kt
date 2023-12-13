package rafa.gomez.videogametrivia.challenge.secondaryadapter.database

import org.springframework.data.repository.findByIdOrNull
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria.ById
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria.ByCategory
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria.ByCategoryAndDifficulty
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria.ByCategory as FindByCategory
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria.ByCategoryAndDifficulty as FindByCategoryAndDifficulty

class MongoChallengeRepository(private val repository: JpaChallengeRepository) : ChallengeRepository {
    override suspend fun find(criteria: FindChallengeCriteria): Challenge? =
        when (criteria) {
            is FindByCategory -> repository.findByCategory(criteria.category.name)
            is FindByCategoryAndDifficulty -> repository.findByCategoryAndDifficulty(criteria.category.name, criteria.difficulty.name())
            is ById -> repository.findByIdOrNull(criteria.id.toString())
        }?.toDomain()

    override suspend fun search(criteria: SearchChallengeCriteria): List<Challenge> =
        when (criteria) {
            is ByCategory -> repository.findAllByCategory(criteria.category.name)
            is ByCategoryAndDifficulty -> repository.findAllByCategoryAndDifficulty(criteria.category.name, criteria.difficulty.name())
        }.map { it.toDomain() }

    override suspend fun save(challenge: Challenge) { repository.save(challenge.toDocument()) }
}
