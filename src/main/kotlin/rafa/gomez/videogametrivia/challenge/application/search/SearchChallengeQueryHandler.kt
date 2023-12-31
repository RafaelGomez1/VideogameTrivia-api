package rafa.gomez.videogametrivia.challenge.application.search

import arrow.core.raise.Raise
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeError.InvalidCategory
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeError.InvalidDifficulty
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.question.domain.Difficulty

class SearchChallengeQueryHandler(repository: ChallengeRepository) {

    private val searchChallenge = ChallengeSearcher(repository)

    context(Raise<SearchChallengeError>)
    suspend fun handle(query: SearchChallengeQuery): List<Challenge> {
        val category = Category.fromString(query.category) ?: raise(InvalidCategory)
        val difficulty = Difficulty.fromString(query.difficulty) ?: raise(InvalidDifficulty)

        return searchChallenge.invoke(category, difficulty)
    }
}

data class SearchChallengeQuery(val category: String, val difficulty: String)
