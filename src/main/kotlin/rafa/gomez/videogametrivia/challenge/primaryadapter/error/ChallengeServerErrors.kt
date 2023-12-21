package rafa.gomez.videogametrivia.challenge.primaryadapter.error

object ChallengeServerErrors {

    val CHALLENGE_NOT_FOUND_ERROR = Pair("10", "Challenge not found")
    val CHALLENGE_FAILED_ERROR = Pair("11", "Challenge failed due to incorrect answers")
    val INVALID_CATEGORY_ERROR = Pair("12", "Invalid Category")
    val INVALID_DIFFICULTY_ERROR = Pair("13", "Invalid Difficulty")
}
