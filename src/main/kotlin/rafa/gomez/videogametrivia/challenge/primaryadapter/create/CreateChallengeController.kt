package rafa.gomez.videogametrivia.challenge.primaryadapter.create

import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import rafa.gomez.videogametrivia.challenge.application.create.CreateChallengesCommand
import rafa.gomez.videogametrivia.challenge.application.create.CreateChallengesCommandHandler
import rafa.gomez.videogametrivia.shared.BaseController
import rafa.gomez.videogametrivia.shared.response.Response

@RestController
class CreateChallengeController(private val handler: CreateChallengesCommandHandler): BaseController() {

    @PostMapping("/challenges")
    fun create(): Response<Unit> = runBlocking {
        Response.ok(handler.handle(CreateChallengesCommand))
    }
}
