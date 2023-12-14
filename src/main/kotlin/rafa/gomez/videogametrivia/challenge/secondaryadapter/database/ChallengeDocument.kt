package rafa.gomez.videogametrivia.challenge.secondaryadapter.database

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Category.NO_CATEGORY
import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.domain.ChallengeId
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Difficulty.Undetermined
import rafa.gomez.videogametrivia.question.secondaryadapter.database.QuestionDocument
import rafa.gomez.videogametrivia.question.secondaryadapter.database.toDocument

interface JpaChallengeRepository : MongoRepository<ChallengeDocument, String> {
    fun findAllByCategory(category: String): List<ChallengeDocument>
    fun findAllByCategoryAndDifficulty(category: String, difficulty: String): List<ChallengeDocument>

    fun findByCategory(category: String): ChallengeDocument?
    fun findByCategoryAndDifficulty(category: String, difficulty: String): ChallengeDocument?
}

@Document(collection = "Challenges")
data class ChallengeDocument(
    @Id
    val id: String,
    val category: String,
    val questions: List<QuestionDocument>,
    val difficulty: String
) {

    fun toDomain(): Challenge =
        Challenge(
            id = ChallengeId.fromString(id),
            category = Category.fromString(category) ?: NO_CATEGORY,
            difficulty = Difficulty.fromString(difficulty) ?: Undetermined,
            questions = questions.map { it.toDomain() }
        )
}

internal fun Challenge.toDocument() =
    ChallengeDocument(
        id = id.toString(),
        category = category.name,
        questions = questions.map { it.toDocument() },
        difficulty = difficulty.name()
    )
