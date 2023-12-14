package rafa.gomez.videogametrivia.challenge.domain

import arrow.core.raise.Raise
import java.util.UUID.randomUUID
import rafa.gomez.videogametrivia.challenge.domain.SolveChallengeDomainError.IncorrectAnswer
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Difficulty.Easy
import rafa.gomez.videogametrivia.question.domain.Difficulty.Hard
import rafa.gomez.videogametrivia.question.domain.Difficulty.Medium
import rafa.gomez.videogametrivia.question.domain.Difficulty.Undetermined
import rafa.gomez.videogametrivia.question.domain.Question
import rafa.gomez.videogametrivia.shared.UserId
import rafa.gomez.videogametrivia.shared.event.aggregate.Aggregate

data class Challenge(
    val id: ChallengeId,
    val category: Category,
    val questions: List<Question>,
    val difficulty: Difficulty
) : Aggregate() {
    companion object {
        fun createMultiple(
            category: Category,
            questions: List<Question>
        ): List<Challenge> =
            questions
                .groupBy { it.difficulty }
                .flatMap { entry ->
                    entry.value
                         .chunked(CHALLENGE_QUESTIONS_AMOUNT)
                         .filter { it.size == CHALLENGE_QUESTIONS_AMOUNT }
                         .map { Challenge(ChallengeId(randomUUID()), category, it, entry.key) }
                 }

        private const val CHALLENGE_QUESTIONS_AMOUNT = 10
    }

    context(Raise<SolveChallengeDomainError>)
    fun solve(answers: List<Answer>, userId: UserId): Challenge =
        if (challengeIsPassed(answers)) copy().pushChallengeSolvedEvent(userId)
        else raise(IncorrectAnswer)

    private fun challengeIsPassed(answers: List<Answer>): Boolean =
        when (difficulty) {
            Easy -> questions.count { question -> question.correctAnswer in answers } >= 8
            Medium -> questions.count { question -> question.correctAnswer in answers } >= 9
            Hard -> questions.all { question -> question.correctAnswer in answers }
            Undetermined -> TODO()
        }
}

sealed interface SolveChallengeDomainError {
    object IncorrectAnswer : SolveChallengeDomainError
}
