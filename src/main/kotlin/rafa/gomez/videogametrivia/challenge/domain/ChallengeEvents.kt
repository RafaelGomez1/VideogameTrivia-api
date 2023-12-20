package rafa.gomez.videogametrivia.challenge.domain

import rafa.gomez.videogametrivia.shared.UserId
import rafa.gomez.videogametrivia.shared.event.challenge.ChallengeSolvedEvent

fun Challenge.pushChallengeSolvedEvent(userId: UserId): Challenge =
    this.also { push(ChallengeSolvedEvent(id.toString(), category.name, difficulty.name(), userId.toString())) }
