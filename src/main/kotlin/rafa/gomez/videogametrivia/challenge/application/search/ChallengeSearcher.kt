package rafa.gomez.videogametrivia.challenge.application.search

import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.SearchChallengeCriteria.ByCategoryAndDifficulty
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.question.domain.Difficulty

class ChallengeSearcher(private val repository: ChallengeRepository) {

    suspend operator fun invoke(category: Category, difficulty: Difficulty): List<Challenge> =
        repository.search(ByCategoryAndDifficulty(category, difficulty))
}

sealed interface SearchChallengeError {
    object InvalidCategory : SearchChallengeError
    object InvalidDifficulty : SearchChallengeError
}
