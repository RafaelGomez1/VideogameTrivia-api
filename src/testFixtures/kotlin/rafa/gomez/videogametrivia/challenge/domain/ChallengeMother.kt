package rafa.gomez.videogametrivia.challenge.domain

import java.util.UUID
import rafa.gomez.videogametrivia.question.QuestionMother
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Question

object ChallengeMother {
    fun random(
        id: ChallengeId = ChallengeIdMother.random(),
        category: Category = Category.values().random(),
        difficulty: Difficulty = Difficulty.Easy,
        questions: List<Question> = (1..10).map { QuestionMother.random() }
    ): Challenge = Challenge(id, category, questions, difficulty)
}

object ChallengeIdMother {
    fun random(value: UUID = UUID.randomUUID()): ChallengeId = ChallengeId(value)
}
