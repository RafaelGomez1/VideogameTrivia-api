package rafa.gomez.videogametrivia.challenge.secondaryadapter.database

import org.springframework.stereotype.Component
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.challenge.domain.FindChallengeCriteria
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria

@Component
class MongoChallengeRepository(private val repository: JpaChallengeRepository): ChallengeRepository {
    override suspend fun find(criteria: FindChallengeCriteria): Challenge? {
        TODO("Not yet implemented")
    }

    override suspend fun search(criteria: SearchChallengeCriteria): List<Challenge> {
        TODO("Not yet implemented")
    }

    override suspend fun save(challenge: Challenge) {
        TODO("Not yet implemented")
    }


}
