package rafa.gomez.videogametrivia.question

import java.util.UUID
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.faker.FakerTest
import rafa.gomez.videogametrivia.question.domain.Choice
import rafa.gomez.videogametrivia.question.domain.CorrectAnswer
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Question
import rafa.gomez.videogametrivia.question.domain.QuestionId
import rafa.gomez.videogametrivia.question.domain.Statement

object QuestionMother {
    fun random(
        id: QuestionId = QuestionIdMother.random(),
        statement: Statement = StatementMother.random(),
        choices: List<Choice> = (0..3).map { ChoiceMother.random() },
        correctAnswer: CorrectAnswer = CorrectAnswerMother.random(),
        category: Category = Category.values().random(),
        difficulty: Difficulty = Difficulty.Easy
    ): Question = Question(
            id = id,
            statement = statement,
            choices = choices,
            correctAnswer = correctAnswer,
            category = category,
            difficulty = difficulty
    )
}

object QuestionIdMother {
    fun random(value: UUID = UUID.randomUUID()): QuestionId = QuestionId(value)
}

object StatementMother {
    fun random(value: String = FakerTest.faker.name.firstName()): Statement = Statement(value)
}

object ChoiceMother {
    fun random(value: String = FakerTest.faker.name.firstName()): Choice = Choice(value)
}

object CorrectAnswerMother {
    fun random(value: String = FakerTest.faker.name.firstName()): CorrectAnswer = CorrectAnswer(value)
}
