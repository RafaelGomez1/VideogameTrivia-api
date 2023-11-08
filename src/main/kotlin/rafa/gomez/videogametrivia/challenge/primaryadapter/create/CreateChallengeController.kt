package rafa.gomez.videogametrivia.challenge.primaryadapter.create

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import rafa.gomez.videogametrivia.challenge.application.create.CreateChallengesCommandHandler
import rafa.gomez.videogametrivia.shared.response.Response

@RestController
class CreateChallengeController(private val handler: CreateChallengesCommandHandler) {

    @PostMapping("/challenges")
    fun create(): Response<*> = TODO()
}
