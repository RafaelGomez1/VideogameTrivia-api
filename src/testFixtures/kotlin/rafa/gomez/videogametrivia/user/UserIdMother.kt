package rafa.gomez.videogametrivia.user

import java.util.UUID
import rafa.gomez.videogametrivia.shared.UserId

object UserIdMother {
    fun random(value: UUID = UUID.randomUUID()): UserId = UserId(value)
}
