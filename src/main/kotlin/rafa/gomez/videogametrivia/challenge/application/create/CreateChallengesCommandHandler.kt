package rafa.gomez.videogametrivia.challenge.application.create

import rafa.gomez.videogametrivia.challenge.domain.Category.CONSOLE_HISTORY
import rafa.gomez.videogametrivia.challenge.domain.Category.LORE_AND_STORYLINES
import rafa.gomez.videogametrivia.challenge.domain.Category.VIDEOGAME_HISTORY
import rafa.gomez.videogametrivia.challenge.domain.ChallengeRepository
import rafa.gomez.videogametrivia.question.domain.QuestionRepository

class CreateChallengesCommandHandler(
    repository: ChallengeRepository,
    questionRepository: QuestionRepository
) {

    private val createChallenges = ChallengesCreator(repository, questionRepository)

    suspend fun handle(command: CreateChallengesCommand) =
        createChallenges(listOf(CONSOLE_HISTORY, VIDEOGAME_HISTORY, LORE_AND_STORYLINES))
}

object CreateChallengesCommand
