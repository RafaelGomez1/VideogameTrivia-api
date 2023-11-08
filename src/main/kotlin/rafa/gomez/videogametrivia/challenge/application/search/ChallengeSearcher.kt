package rafa.gomez.videogametrivia.challenge.application.search

import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.QuestionRepository
import rafa.gomez.videogametrivia.question.domain.SearchQuestionCriteria.ByCategoryAndDifficulty
import rafa.gomez.videogametrivia.question.domain.findOrElse

class ChallengeSearcher(private val repository: QuestionRepository) {

    suspend fun invoke(category: Category, difficulty: Difficulty): List<Challenge> {
        val questions = repository.search(ByCategoryAndDifficulty(category, difficulty))

        return Challenge.createMultiple(category, questions, difficulty)
    }

}

sealed interface SearchChallengeError {
    object InvalidCategory : SearchChallengeError
    object InvalidDifficulty : SearchChallengeError
}
