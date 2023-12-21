package rafa.gomez.videogametrivia.challenge.solve

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import rafa.gomez.videogametrivia.challenge.domain.ChallengeMother
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeCommandHandler
import rafa.gomez.videogametrivia.challenge.domain.ChallengeSolvedEventMother
import rafa.gomez.videogametrivia.challenge.fakes.FakeChallengeRepository
import rafa.gomez.videogametrivia.challenge.primaryadapter.SolveChallengeDTOMother
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.CHALLENGE_FAILED_ERROR
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.CHALLENGE_NOT_FOUND_ERROR
import rafa.gomez.videogametrivia.challenge.primaryadapter.solve.SolveChallengeController
import rafa.gomez.videogametrivia.shared.error.ServerError
import rafa.gomez.videogametrivia.shared.fakes.FakeDomainEventPublisher
import rafa.gomez.videogametrivia.user.UserIdMother

@OptIn(ExperimentalCoroutinesApi::class)
class SolveChallengeTest {

    private val repository = FakeChallengeRepository
    private val publisher = FakeDomainEventPublisher

    private val handler = SolveChallengeCommandHandler(repository, publisher)

    private val controller = SolveChallengeController(handler)

    @BeforeEach
    fun setUp() {
        repository.resetFake()
        publisher.resetFake()
    }

    @Test
    fun `should return OK if the challenge was solved correctly`() = runTest {
        // Given
        repository.save(challenge)

        // When
        val result = controller.solve(challenge.id.toString(), challengeSolvedDTO)

        // Then
        assertEquals(OK, result.statusCode)
        assertEquals(null, result.body)

        assertTrue { repository.contains(challenge) }
        assertTrue { publisher.published(challengedSolvedEvent) }
    }

    @Test
    fun `should return BAD_REQUEST if the challenge is not solved correctly`() = runTest {
        // Given
        repository.save(challenge)

        // When
        val result = controller.solve(challenge.id.toString(), challengeFailedDTO)

        // Then
        assertEquals(BAD_REQUEST, result.statusCode)
        assertEquals(ServerError.of(CHALLENGE_FAILED_ERROR), result.body)

        assertTrue { repository.contains(challenge) }
        assertTrue { publisher.noEventsPublished() }
    }

    @Test
    fun `should return NOT_FOUND if the challenge is not found`() = runTest {
        // When
        val result = controller.solve(challenge.id.toString(), challengeFailedDTO)

        // Then
        assertEquals(NOT_FOUND, result.statusCode)
        assertEquals(ServerError.of(CHALLENGE_NOT_FOUND_ERROR), result.body)

        assertFalse { repository.contains(challenge) }
        assertTrue() { publisher.noEventsPublished() }
    }

    private val userId = UserIdMother.random()
    private val challenge = ChallengeMother.random()
    private val challengeSolvedDTO = SolveChallengeDTOMother.solutionsFromChallenge(challenge, userId)
    private val challengeFailedDTO = SolveChallengeDTOMother.fromChallenge(challenge, userId)

    private val challengedSolvedEvent = ChallengeSolvedEventMother.fromChallenge(challenge, userId)
}
