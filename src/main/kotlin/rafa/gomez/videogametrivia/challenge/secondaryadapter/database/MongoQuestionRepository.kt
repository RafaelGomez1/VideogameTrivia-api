package rafa.gomez.videogametrivia.challenge.secondaryadapter.database

import java.util.UUID
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import rafa.gomez.videogametrivia.challenge.domain.FindQuestionCriteria
import rafa.gomez.videogametrivia.challenge.domain.FindQuestionCriteria.ById
import rafa.gomez.videogametrivia.challenge.domain.Question
import rafa.gomez.videogametrivia.challenge.domain.QuestionRepository
import rafa.gomez.videogametrivia.challenge.domain.SearchQuestionCriteria
import rafa.gomez.videogametrivia.challenge.domain.SearchQuestionCriteria.ByCategory
import rafa.gomez.videogametrivia.challenge.domain.SearchQuestionCriteria.ByCategoryAndDifficulty


@Repository
interface JpaQuestionRepository : MongoRepository<QuestionDocument, String> {
    fun findAllByCategory(category: String): List<QuestionDocument>
    fun findAllByCategoryAndDifficulty(category: String, difficulty: String): List<QuestionDocument>
}

@Component
class MongoQuestionRepository(private val jpaRepository: JpaQuestionRepository): QuestionRepository {
    override suspend fun find(criteria: FindQuestionCriteria): Question? =
        when(criteria) {
            is ById -> jpaRepository.findByIdOrNull(criteria.id.toString())
        }?.toDomain()


    override suspend fun search(criteria: SearchQuestionCriteria): List<Question> =
        when(criteria) {
            is ByCategory -> jpaRepository.findAllByCategory(criteria.category.name)
            is ByCategoryAndDifficulty -> jpaRepository.findAllByCategoryAndDifficulty(criteria.category.name, criteria.difficulty.name())
        }.map { it.toDomain() }

    override suspend fun save(question: Question) { jpaRepository.save(question.toDocument()) }
}
