package rafa.gomez.videogametrivia.question.secondaryadapter.gpt

import java.util.UUID
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.question.domain.Choice
import rafa.gomez.videogametrivia.question.domain.CorrectAnswer
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Difficulty.Undetermined
import rafa.gomez.videogametrivia.question.domain.Question
import rafa.gomez.videogametrivia.question.domain.QuestionId
import rafa.gomez.videogametrivia.question.domain.Statement

class QuestionInterpreter {

    fun toQuestions(category: Category, response: String): List<Question> {
        val lines = response.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
        val questions = mutableListOf<Question>()

        var questionId = UUID.randomUUID()
        var statement = ""
        var topic = ""
        var difficulty: Difficulty = Undetermined
        val choices = mutableListOf<Choice>()
        var correctAnswer = ""

        for (line in lines) {
            when {
                line.startsWith("Difficulty: ") -> difficulty = Difficulty.fromString(line.substring(11).trim())!!
                line.startsWith("Topic: ") -> topic = line.substring(6).trim()
                line.startsWith("A.") -> choices.add(Choice(line.substring(3).trim()))
                line.startsWith("B.") -> choices.add(Choice(line.substring(3).trim()))
                line.startsWith("C.") -> choices.add(Choice(line.substring(3).trim()))
                line.startsWith("D.") -> choices.add(Choice(line.substring(3).trim()))

                line.startsWith("Answer:") -> {
                    correctAnswer = line.substring(line.indexOf(':') + 4).trim()
                }
                line.startsWith("Q: ") || line.matches(Regex("\\d+\\..+")) -> {
                    if (statement.isNotBlank()) {
                        val question = Question(
                            id = QuestionId(questionId),
                            statement = Statement(statement),
                            choices = choices.toList(),
                            correctAnswer = CorrectAnswer(correctAnswer),
                            category = Category.fromModel(topic) ?: category,
                            difficulty = difficulty
                        )
                        questions.add(question)

                        // Reset variables for the next question
                        questionId = UUID.randomUUID()
                        statement = ""
                        choices.clear()
                        correctAnswer = ""
                        difficulty = Undetermined
                    }
                    statement = line.substring(line.indexOf('.') + 3).trim()
                }
            }
        }

        // Add the last question
        if (statement.isNotBlank()) {
            val question = Question(
                id = QuestionId(questionId),
                statement = Statement(statement),
                choices = choices.toList(),
                correctAnswer = CorrectAnswer(correctAnswer),
                category = Category.fromModel(topic) ?: category,
                difficulty = difficulty
            )
            questions.add(question)
        }

        return questions.removeQuestionIfAnswerIsNotInChoices()
    }

    private fun List<Question>.removeQuestionIfAnswerIsNotInChoices(): List<Question> =
        also { println("Questions before filtering: $size") }
            .filter { question ->
                question.choices
                    .map { choice -> choice.value.lowercase() }
                    .contains(question.correctAnswer.value.lowercase())
            }
            .also { println("Questions after filtering: $size") }
}
