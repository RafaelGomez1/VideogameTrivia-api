package rafa.gomez.videogametrivia.challenge.domain

interface QuestionGenerator {
    suspend fun generate(category: Category, difficulty: Difficulty, number: Number): List<Question>
}


