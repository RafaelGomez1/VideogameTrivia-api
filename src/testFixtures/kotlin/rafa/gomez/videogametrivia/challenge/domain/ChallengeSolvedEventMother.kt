package rafa.gomez.videogametrivia.challenge.domain

import rafa.gomez.videogametrivia.shared.UserId
import rafa.gomez.videogametrivia.shared.event.challenge.ChallengeSolvedEvent

object ChallengeSolvedEventMother {

    fun fromChallenge(challenge: Challenge, userId: UserId) =
        with(challenge) {
            ChallengeSolvedEvent(id.toString(), category.name, difficulty.name(), userId.toString())
        }
}
