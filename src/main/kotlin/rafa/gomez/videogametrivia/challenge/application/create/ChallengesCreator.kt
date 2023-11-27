package rafa.gomez.videogametrivia.challenge.application.create

import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.question.domain.QuestionRepository

class ChallengesCreator(
    private val repository: ChallengeRepository,
    private val questionRepository: QuestionRepository
) {

    fun invoke(categories: List<Category>) {
        //

    }
}
