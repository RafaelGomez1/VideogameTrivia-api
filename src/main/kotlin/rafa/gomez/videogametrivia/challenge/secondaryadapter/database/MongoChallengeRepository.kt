package rafa.gomez.videogametrivia.challenge.secondaryadapter.database

import org.springframework.stereotype.Component
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.ChallengeCriteria
import rafa.gomez.videogametrivia.challenge.domain.ChallengeCriteria.ByCategory
import rafa.gomez.videogametrivia.challenge.domain.ChallengeCriteria.ByCategoryAndDifficulty
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository

@Component
class MongoChallengeRepository(private val repository: JpaChallengeRepository): ChallengeRepository {
    override suspend fun find(criteria: ChallengeCriteria): Challenge? =
        when(criteria) {
            is ByCategory -> repository.findByCategory(criteria.category.name)
            is ByCategoryAndDifficulty -> repository.findByCategoryAndDifficulty(criteria.category.name, criteria.difficulty.name())
        }?.toDomain()

    override suspend fun search(criteria: ChallengeCriteria): List<Challenge> =
        when(criteria) {
            is ByCategory -> repository.findAllByCategory(criteria.category.name)
            is ByCategoryAndDifficulty -> repository.findAllByCategoryAndDifficulty(criteria.category.name, criteria.difficulty.name())
        }.map { it.toDomain() }

    override suspend fun save(challenge: Challenge) { repository.save(challenge.toDocument()) }
}
