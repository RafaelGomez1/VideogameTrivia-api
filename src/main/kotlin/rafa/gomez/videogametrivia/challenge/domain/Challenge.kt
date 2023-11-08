package rafa.gomez.videogametrivia.challenge.domain

import java.util.UUID
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Question

data class Challenge(
    val id: ChallengeId,
    val category: Category,
    val questions: List<Question>,
    val difficulty: Difficulty
) {
    companion object {
        fun createMultiple(
            category: Category,
            questions: List<Question>,
            difficulty: Difficulty
        ) =
            questions.chunked(CHALLENGE_QUESTIONS_AMOUNT)
                .filter { it.size == CHALLENGE_QUESTIONS_AMOUNT }
                .map { Challenge(ChallengeId(UUID.randomUUID()), category, it, difficulty) }

        private const val CHALLENGE_QUESTIONS_AMOUNT = 10
    }
}
