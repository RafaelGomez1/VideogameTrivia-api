package rafa.gomez.videogametrivia.challenge.primaryadapter.search

import arrow.core.raise.fold
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeError
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeError.InvalidCategory
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeError.InvalidDifficulty
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeQuery
import rafa.gomez.videogametrivia.challenge.application.search.SearchChallengeQueryHandler
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.INVALID_CATEGORY_ERROR
import rafa.gomez.videogametrivia.challenge.primaryadapter.error.ChallengeServerErrors.INVALID_DIFFICULTY_ERROR
import rafa.gomez.videogametrivia.shared.BaseController
import rafa.gomez.videogametrivia.shared.response.Response
import rafa.gomez.videogametrivia.shared.response.withBody

@RestController
class SearchChallengeController(private val handler: SearchChallengeQueryHandler) : BaseController() {

    @GetMapping("/challenges")
    fun search(
        @RequestParam(name = "category") category: String,
        @RequestParam(name = "difficulty") difficulty: String
    ): Response<*> = runBlocking {
        fold(
            block = { handler.handle(SearchChallengeQuery(category, difficulty)) },
            transform = { Response.status(OK).body(it.toDto()) },
            recover = { error -> error.toServerResponse() }
        )
    }

    private fun SearchChallengeError.toServerResponse(): Response<*> =
        when (this) {
            InvalidCategory -> Response.status(BAD_REQUEST).withBody(INVALID_CATEGORY_ERROR)
            InvalidDifficulty -> Response.status(BAD_REQUEST).withBody(INVALID_DIFFICULTY_ERROR)
        }
}
