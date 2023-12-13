package rafa.gomez.videogametrivia.challenge.primaryadapter.solve

import arrow.core.raise.fold
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeCommand
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeCommandHandler
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeError
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeError.ChallengeFailed
import rafa.gomez.videogametrivia.challenge.application.solve.SolveChallengeError.NotFound
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.CHALLENGE_FAILED_ERROR
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.CHALLENGE_NOT_FOUND_ERROR
import rafa.gomez.videogametrivia.shared.BaseController
import rafa.gomez.videogametrivia.shared.response.Response
import rafa.gomez.videogametrivia.shared.response.withBody
import rafa.gomez.videogametrivia.shared.response.withoutBody

@RestController
class SolveChallengeController(private val handler: SolveChallengeCommandHandler) : BaseController() {

    @PostMapping("/challenges/{challengeId}/solve")
    fun solve(
        @PathVariable challengeId: String,
        @RequestBody body: SolveChallengeDTO
    ): Response<*> = runBlocking {
        fold(
            block = { handler.handle(SolveChallengeCommand(challengeId, body.answers, body.userId)) },
            transform = { Response.status(OK).withoutBody() },
            recover = { error -> error.toServerError() }
        )
    }

    private fun SolveChallengeError.toServerError(): Response<*> =
        when (this) {
            ChallengeFailed -> Response.status(BAD_REQUEST).withBody(CHALLENGE_FAILED_ERROR)
            NotFound -> Response.status(NOT_FOUND).withBody(CHALLENGE_NOT_FOUND_ERROR)
        }
}
