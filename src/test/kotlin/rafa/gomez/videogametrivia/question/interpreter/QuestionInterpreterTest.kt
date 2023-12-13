package rafa.gomez.videogametrivia.question.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import rafa.gomez.videogametrivia.challenge.PromptResponseMother
import rafa.gomez.videogametrivia.challenge.domain.Category.LORE_AND_STORYLINES
import rafa.gomez.videogametrivia.question.secondaryadapter.gpt.QuestionInterpreter

class QuestionInterpreterTest {

    private val interpreter = QuestionInterpreter()

    @Test
    fun `should return a list of questions based on the response from chat gpt`() {
        // When
        val response = interpreter.toQuestions(LORE_AND_STORYLINES, validResponse)

        // Then
        assertEquals(4, response.size)
        assertTrue { response.all { it.category == LORE_AND_STORYLINES } }
        assertEquals(4, response.random().choices.size)
    }

    @Test
    fun `should return a list of questions excluding the ones where the answer is not part of the choices`() {
        // When
        val response = interpreter.toQuestions(LORE_AND_STORYLINES, invalidAnswerResponse)

        // Then
        assertEquals(3, response.size)
        assertTrue { response.all { it.category == LORE_AND_STORYLINES } }
        assertEquals(4, response.random().choices.size)
    }

    @Test
    fun `should return a list of questions with the given topic if it's not present in the response`() {
        // When
        val response = interpreter.toQuestions(LORE_AND_STORYLINES, topiclessResponse)

        // Then
        assertEquals(4, response.size)
        assertTrue { response.all { it.category == LORE_AND_STORYLINES } }
        assertEquals(4, response.random().choices.size)
    }

    private val invalidAnswerResponse = PromptResponseMother.answerDoesNotMatchChoice()
    private val topiclessResponse = PromptResponseMother.topicLessResponse()
    private val validResponse = PromptResponseMother.fullValidResponse()
}
