package rafa.gomez.videogametrivia.question.domain

import java.util.UUID
import rafa.gomez.videogametrivia.challenge.domain.Category

data class Question(
    val id: QuestionId,
    val statement: Statement,
    val choices: List<Choice>,
    val correctAnswer: CorrectAnswer,
    val category: Category,
    val difficulty: Difficulty
)

@JvmInline
value class QuestionId(val value: UUID) {
    override fun toString(): String = value.toString()
}

@JvmInline
value class Statement(val value: String)

@JvmInline
value class Choice(val value: String)

@JvmInline
value class CorrectAnswer(val value: String)

sealed class Difficulty {
    object Easy : Difficulty()
    object Medium : Difficulty()
    object Hard : Difficulty()
    object Undetermined : Difficulty()

    fun name() =
        when (this) {
            Easy -> "EASY"
            Medium -> "MEDIUM"
            Hard -> "HARD"
            Undetermined -> "UNDETERMINED"
        }
    companion object {
        fun fromString(value: String): Difficulty? =
            when (value) {
                "Hard" -> Hard
                "HARD" -> Hard
                "Easy" -> Easy
                "EASY" -> Easy
                "Medium" -> Medium
                "MEDIUM" -> Medium
                else -> null
            }
    }
}
