package rafa.gomez.videogametrivia.challenge.secondaryadapter.database

import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Category.NO_CATEGORY
import rafa.gomez.videogametrivia.challenge.domain.Choice
import rafa.gomez.videogametrivia.challenge.domain.CorrectAnswer
import rafa.gomez.videogametrivia.challenge.domain.Difficulty
import rafa.gomez.videogametrivia.challenge.domain.Question
import rafa.gomez.videogametrivia.challenge.domain.QuestionId
import rafa.gomez.videogametrivia.challenge.domain.Statement

@Document(collection = "Questions")
data class QuestionDocument(
    @Id
    val id: String,
    val statement: String,
    val choices: List<String>,
    val correctAnswer: String,
    val category: String,
    val difficulty: String
) {

    fun toDomain() = Question(
        id = QuestionId(UUID.fromString(id)),
        statement = Statement(statement),
        choices = choices.map { Choice(it) },
        correctAnswer = CorrectAnswer(correctAnswer),
        category = Category.fromString(category) ?: NO_CATEGORY,
        difficulty = Difficulty.fromString(difficulty) ?: Difficulty.Undertermined
    )
}

internal fun Question.toDocument() = QuestionDocument(
    id = id.toString(),
    statement = statement.value,
    choices = choices.map { it.value },
    correctAnswer = correctAnswer.value,
    category = category.name,
    difficulty = difficulty.name()
)
