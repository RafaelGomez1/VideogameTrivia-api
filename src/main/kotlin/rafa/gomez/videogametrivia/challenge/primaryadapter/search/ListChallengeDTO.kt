package rafa.gomez.videogametrivia.challenge.primaryadapter.search

import rafa.gomez.videogametrivia.challenge.domain.Challenge

data class ChallengeDTO(
    val category: String,
    val questions: List<QuestionDTO>,
    val difficulty: String
)

data class QuestionDTO(
    val id: String,
    val statement: String,
    val choices: List<String>,
    val correctAnswer: String,
    val category: String,
    val difficulty: String
)

internal fun List<Challenge>.toDto(): List<ChallengeDTO> = map { it.toDto() }

internal fun Challenge.toDto(): ChallengeDTO =
    ChallengeDTO(
        category = category.name,
        questions = questions.map { question ->
            QuestionDTO(
                id = question.id.toString(),
                statement = question.statement.value,
                choices = question.choices.map { it.value },
                correctAnswer = question.correctAnswer.value,
                category = question.category.name,
                difficulty = question.difficulty.name()
            )
        },
        difficulty = difficulty.name()
    )
