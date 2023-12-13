package rafa.gomez.videogametrivia.challenge.domain

import java.util.UUID
import rafa.gomez.videogametrivia.question.domain.CorrectAnswer

@JvmInline
value class ChallengeId(val value: UUID) {
    override fun toString(): String = value.toString()

    companion object {
        fun fromString(value: String) = ChallengeId(UUID.fromString(value))
    }
}

@JvmInline
value class Answer(val value: String)

operator fun List<Answer>.contains(element: CorrectAnswer): Boolean = any { it.value == element.value }

enum class Category {
    VIDEOGAME_HISTORY,
    EASTER_EGGS,
    CHARACTERS,
    LORE_AND_STORYLINES,
    CONSOLE_HISTORY,
    DEVELOPMENT_STUDIOS,
    MECHANICS,
    FUN_FACTS,
    NO_CATEGORY;

    fun toModel(): String = name.replace("_", " ").lowercase()

    companion object {
        fun fromString(input: String): Category? = values().find { input.lowercase() == it.name.lowercase() }
        fun fromModel(input: String): Category? = values().find { input.lowercase().contains(it.name.replace("_", " ").lowercase()) }
    }
}
