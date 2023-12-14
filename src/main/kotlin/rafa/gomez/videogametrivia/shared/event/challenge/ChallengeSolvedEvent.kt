package rafa.gomez.videogametrivia.shared.event.challenge

import rafa.gomez.videogametrivia.shared.event.bus.DomainEvent

data class ChallengeSolvedEvent(
    val id: String,
    val category: String,
    val difficulty: String,
    val userId: String
) : DomainEvent(id)
