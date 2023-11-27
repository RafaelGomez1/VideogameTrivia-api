package rafa.gomez.videogametrivia.challenge.application.create

import org.springframework.stereotype.Component
import rafa.gomez.videogametrivia.challenge.domain.Category.CONSOLE_HISTORY
import rafa.gomez.videogametrivia.challenge.domain.Category.LORE_AND_STORYLINES
import rafa.gomez.videogametrivia.challenge.domain.Category.VIDEOGAME_HISTORY
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.question.domain.QuestionRepository

@Component
class CreateChallengesCommandHandler(
    repository: ChallengeRepository,
    questionRepository: QuestionRepository
) {

    private val creator = ChallengesCreator(repository, questionRepository)

    fun handle(command: CreateChallengesCommand) =
        creator.invoke(listOf(CONSOLE_HISTORY, VIDEOGAME_HISTORY, LORE_AND_STORYLINES))
}

object CreateChallengesCommand
