package rafa.gomez.videogametrivia.challenge.application.create

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.question.domain.QuestionRepository
import rafa.gomez.videogametrivia.question.domain.SearchQuestionCriteria.ByCategory

class ChallengesCreator(
    private val repository: ChallengeRepository,
    private val questionRepository: QuestionRepository
) {

    suspend operator fun invoke(categories: List<Category>): Unit = coroutineScope {
        val challenges = categories.flatMap { category ->
            val questions = async { questionRepository.search(ByCategory(category)) }

            Challenge.createMultiple(category, questions.await())
        }

        challenges.forEach { repository.save(it) }
    }
}
