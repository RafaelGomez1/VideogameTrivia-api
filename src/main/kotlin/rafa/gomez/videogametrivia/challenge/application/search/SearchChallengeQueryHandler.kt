package rafa.gomez.videogametrivia.challenge.application.search

import arrow.core.raise.Raise
import org.springframework.stereotype.Component
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeError.InvalidCategory
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeError.InvalidDifficulty
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.QuestionRepository

@Component
class SearchChallengeQueryHandler(repository: QuestionRepository) {

    private val searcher = ChallengeSearcher(repository)

    context(Raise<SearchChallengeError>)
    suspend fun handle(query: SearchChallengeQuery): List<Challenge> {
        val category = Category.fromString(query.category) ?: raise(InvalidCategory)
        val difficulty = Difficulty.fromString(query.difficulty) ?: raise(InvalidDifficulty)

        return searcher.invoke(category, difficulty)
    }

}

data class SearchChallengeQuery(val category: String, val difficulty: String)
