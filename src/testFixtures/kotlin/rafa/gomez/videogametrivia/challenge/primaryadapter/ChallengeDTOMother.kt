package rafa.gomez.videogametrivia.challenge.primaryadapter

import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.primaryadapter.search.ChallengeDTO
import rafa.gomez.videogametrivia.challenge.primaryadapter.search.QuestionDTO

object ChallengeDTOMother {

    fun fromChallenges(vararg challenge: Challenge): List<ChallengeDTO> =
        challenge.map { fromChallenge(it) }

    fun fromChallenge(challenge: Challenge): ChallengeDTO =
        with(challenge) {
            ChallengeDTO(
                category = category.name,
                questions = questions.map { question ->
                    QuestionDTO(
                        id = question.id.toString(),
                        statement = question.statement.value,
                        choices = question.choices.map { it.value },
                        correctAnswer = question.correctAnswer.value,
                        category = question.category.name,
                        difficulty = question.difficulty.name()
                    )
                },
                difficulty = difficulty.name()
            )
        }
}
