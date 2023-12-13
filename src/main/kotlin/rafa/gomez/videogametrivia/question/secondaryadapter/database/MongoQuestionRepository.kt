package rafa.gomez.videogametrivia.question.secondaryadapter.database

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import rafa.gomez.videogametrivia.question.domain.FindQuestionCriteria
import rafa.gomez.videogametrivia.question.domain.FindQuestionCriteria.ById
import rafa.gomez.videogametrivia.question.domain.Question
import rafa.gomez.videogametrivia.question.domain.QuestionRepository
import rafa.gomez.videogametrivia.question.domain.SearchQuestionCriteria
import rafa.gomez.videogametrivia.question.domain.SearchQuestionCriteria.ByCategory
import rafa.gomez.videogametrivia.question.domain.SearchQuestionCriteria.ByCategoryAndDifficulty

interface JpaQuestionRepository : MongoRepository<QuestionDocument, String> {
    fun findAllByCategory(category: String): List<QuestionDocument>
    fun findAllByCategoryAndDifficulty(category: String, difficulty: String): List<QuestionDocument>
}

class MongoQuestionRepository(private val jpaRepository: JpaQuestionRepository) : QuestionRepository {
    override suspend fun find(criteria: FindQuestionCriteria): Question? =
        when (criteria) {
            is ById -> jpaRepository.findByIdOrNull(criteria.id.toString())
        }?.toDomain()

    override suspend fun search(criteria: SearchQuestionCriteria): List<Question> =
        when (criteria) {
            is ByCategory -> jpaRepository.findAllByCategory(criteria.category.name)
            is ByCategoryAndDifficulty -> jpaRepository.findAllByCategoryAndDifficulty(criteria.category.name, criteria.difficulty.name())
        }.map { it.toDomain() }

    override suspend fun save(question: Question) { jpaRepository.save(question.toDocument()) }
}
