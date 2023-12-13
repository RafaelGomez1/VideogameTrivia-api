package rafa.gomez.videogametrivia.question.domain

import rafa.gomez.videogametrivia.challenge.domain.Category

interface QuestionGenerator {
    suspend fun generate(category: Category, difficulty: Difficulty, number: Number): List<Question>
}
