package rafa.gomez.videogametrivia.question.application.create

import org.springframework.stereotype.Component
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.QuestionGenerator
import rafa.gomez.videogametrivia.question.domain.QuestionRepository

@Component
class QuestionCreator(
    private val repository: QuestionRepository,
    private val generator: QuestionGenerator
) {

    suspend operator fun invoke(category: Category, difficulty: Difficulty, number: Number) {
        generator.generate(category, difficulty, number)
            .map { repository.save(it) }
            .also { println("${it.size} questions created for category ${category.name} and difficulty ${difficulty.name()}. but $number were requested") }
    }

}
