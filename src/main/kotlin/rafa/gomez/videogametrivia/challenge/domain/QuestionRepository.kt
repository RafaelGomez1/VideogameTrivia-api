package rafa.gomez.videogametrivia.challenge.domain

import arrow.core.raise.Raise

interface QuestionRepository {

    suspend fun find(criteria: FindQuestionCriteria): Question?
    suspend fun search(criteria: SearchQuestionCriteria): List<Question>
    suspend fun save(question: Question)
}

context(Raise<Error>)
suspend fun <Error> QuestionRepository.findOrElse(criteria: FindQuestionCriteria, onError: () -> Error): Question =
    find(criteria) ?: raise(onError())

sealed class FindQuestionCriteria {
    class ById(val id: QuestionId): FindQuestionCriteria()
}

sealed class SearchQuestionCriteria {
    class ByCategory(val category: Category): SearchQuestionCriteria()
    class ByCategoryAndDifficulty(val category: Category, val difficulty: Difficulty): SearchQuestionCriteria()
}
