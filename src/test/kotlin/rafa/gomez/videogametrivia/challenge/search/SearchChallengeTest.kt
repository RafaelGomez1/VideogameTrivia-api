package rafa.gomez.videogametrivia.challenge.search

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.OK
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeQueryHandler
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Category.LORE_AND_STORYLINES
import rafa.gomez.videogametrivia.challenge.domain.Category.VIDEOGAME_HISTORY
import rafa.gomez.videogametrivia.challenge.domain.ChallengeMother
import rafa.gomez.videogametrivia.challenge.fakes.FakeChallengeRepository
import rafa.gomez.videogametrivia.challenge.primaryadapter.ChallengeDTOMother
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.INVALID_CATEGORY_ERROR
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.INVALID_DIFFICULTY_ERROR
import rafa.gomez.videogametrivia.challenge.primaryadapter.search.ChallengeDTO
import rafa.gomez.videogametrivia.challenge.primaryadapter.search.SearchChallengeController
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Difficulty.Easy
import rafa.gomez.videogametrivia.question.domain.Difficulty.Hard
import rafa.gomez.videogametrivia.question.domain.Difficulty.Medium
import rafa.gomez.videogametrivia.shared.error.ServerError

class SearchChallengeTest {

    private val repository = FakeChallengeRepository

    private val handler = SearchChallengeQueryHandler(repository)

    private val controller = SearchChallengeController(handler)

    @BeforeEach
    fun setUp() {
        repository.resetFake()
    }

    @Test
    fun `should return OK with all challenges for the given category and difficulty`() = runTest {
        // Given
        repository.save(hardChallenge1)
        repository.save(hardChallenge2)
        repository.save(hardChallenge3)

        // When
        val result = controller.search(category = LORE_AND_STORYLINES.name, difficulty = Hard.name())

        // Then
        assertEquals(OK, result.statusCode)
        assertEquals(expectedResult, result.body)
    }

    @Test
    fun `should return OK with empty list if there are no challenges for the given category and difficulty`() = runTest {
        // Given
        repository.save(hardChallenge1)
        repository.save(hardChallenge2)
        repository.save(hardChallenge3)

        // When
        val result = controller.search(category = VIDEOGAME_HISTORY.name, difficulty = Easy.name())

        // Then
        assertEquals(OK, result.statusCode)
        assertEquals(emptyList<ChallengeDTO>(), result.body)
    }

    @Test
    fun `should return BAD_REQUEST when category is not valid`() = runTest {
        // Given
        repository.save(hardChallenge1)

        // When
        val result = controller.search(category = "invalid category name", difficulty = Easy.name())

        // Then
        assertEquals(BAD_REQUEST, result.statusCode)
        assertEquals(ServerError.of(INVALID_CATEGORY_ERROR), result.body)
    }

    @Test
    fun `should return BAD_REQUEST when difficulty is not valid`() = runTest {
        // Given
        repository.save(hardChallenge1)

        // When
        val result = controller.search(category = VIDEOGAME_HISTORY.name, difficulty = "invalid difficulty name")

        // Then
        assertEquals(BAD_REQUEST, result.statusCode)
        assertEquals(ServerError.of(INVALID_DIFFICULTY_ERROR), result.body)
    }

    private val hardChallenge1 = ChallengeMother.random(difficulty = Hard, category = LORE_AND_STORYLINES)
    private val hardChallenge2 = ChallengeMother.random(difficulty = Hard, category = LORE_AND_STORYLINES)
    private val hardChallenge3 = ChallengeMother.random(difficulty = Hard, category = LORE_AND_STORYLINES)

    private val expectedResult = ChallengeDTOMother.fromChallenges(hardChallenge1, hardChallenge2, hardChallenge3)
}
