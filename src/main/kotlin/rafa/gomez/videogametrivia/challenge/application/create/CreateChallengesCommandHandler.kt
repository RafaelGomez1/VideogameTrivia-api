package rafa.gomez.videogametrivia.challenge.application.create

import org.springframework.stereotype.Component
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.challenge.domain.Category.CONSOLE_HISTORY
import rafa.gomez.videogametrivia.challenge.domain.Category.LORE_AND_STORYLINES
import rafa.gomez.videogametrivia.challenge.domain.Category.VIDEOGAME_HISTORY
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository

@Component
class CreateChallengesCommandHandler(repository: ChallengeRepository) {

    private val creator = ChallengesCreator(repository)

    fun handle(command: CreateChallengesCommand) =
        creator.invoke(listOf(CONSOLE_HISTORY, VIDEOGAME_HISTORY, LORE_AND_STORYLINES))
}

object CreateChallengesCommand
