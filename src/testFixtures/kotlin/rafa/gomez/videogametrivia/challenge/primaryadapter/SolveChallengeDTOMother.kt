package rafa.gomez.videogametrivia.challenge.primaryadapter

import rafa.gomez.videogametrivia.challenge.domain.Challenge
import rafa.gomez.videogametrivia.challenge.primaryadapter.solve.SolveChallengeDTO
import rafa.gomez.videogametrivia.shared.UserId
import rafa.gomez.videogametrivia.user.UserIdMother

object SolveChallengeDTOMother {

    fun fromChallenge(challenge: Challenge, userId: UserId = UserIdMother.random()) =
        SolveChallengeDTO(
            answers = challenge.questions.map { it.choices.random().value },
            userId = userId.toString()
        )

    fun solutionsFromChallenge(challenge: Challenge, userId: UserId = UserIdMother.random()) =
        SolveChallengeDTO(
            answers = challenge.questions.map { it.correctAnswer.value },
            userId = userId.toString()
        )
}
