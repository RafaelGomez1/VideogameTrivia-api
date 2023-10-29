package rafa.gomez.videogametrivia.challenge.domain

data class Challenge private constructor(
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
                .map { Challenge(category, it, difficulty) }

        private const val CHALLENGE_QUESTIONS_AMOUNT = 10
    }
}
