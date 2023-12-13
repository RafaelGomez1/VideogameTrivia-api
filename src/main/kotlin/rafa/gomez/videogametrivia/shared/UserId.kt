package rafa.gomez.videogametrivia.shared

import java.util.UUID

@JvmInline
value class UserId(val value: UUID) {
    override fun toString(): String = value.toString()

    companion object {
        fun fromString(value: String) = UserId(UUID.fromString(value))
    }
}
