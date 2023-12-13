package rafa.gomez.videogametrivia.question.secondaryadapter.gpt

import io.github.flashvayne.chatgpt.service.ChatgptService
import rafa.gomez.videogametrivia.challenge.domain.Category
import rafa.gomez.videogametrivia.question.domain.Difficulty
import rafa.gomez.videogametrivia.question.domain.Question
import rafa.gomez.videogametrivia.question.domain.QuestionGenerator

class ChatGPTQuestionGenerator(private val gpt: ChatgptService) : QuestionGenerator {

    private val interpreter = QuestionInterpreter()

    override suspend fun generate(category: Category, difficulty: Difficulty, number: Number): List<Question> =
        gpt.sendMessage(prompt.replacePatternsWith(category, difficulty, number))
            .let { response -> interpreter.toQuestions(category, response) }

    private fun String.replacePatternsWith(category: Category, difficulty: Difficulty, number: Number) =
        prompt
            .replace(topicPattern, category.toModel())
            .replace(difficultyPattern, difficulty.name())
            .replace(numberPattern, number.toString())

    private companion object {
        private const val topicPattern = "@@TOPIC_NAME@@"
        private const val numberPattern = "@@NUMBER_OF_QUESTIONS@@"
        private const val difficultyPattern = "@@DIFFICULTY@@"

        private const val prompt = "I want to create a daily videogame challenge/trivia app that provides a question from videogame history (characters, easter eggs, whatever) every day to the end users. Create $numberPattern examples of questions with 4 possible answers and tell me the valid answer and difficulty should be $difficultyPattern. Questions must follow the topic of $topicPattern and must be from 1990 to present day.In order to identify it add the name of the topic at the beginning of the whole response like 'topic: $topicPattern' but avoid adding the timeline in the response. Also add the difficulty level for every question as difficulty: after the question's answer." +
                "Follow this example as a pattern:\n " +
                "Topic: $topicPattern\n" +
                "Q: In which Splatoon 2 game mode do players attempt to capture and control points on a map?\n" +
                "\n" +
                "A. Tower Control\n" +
                "B. Rainmaker\n" +
                "C. Splat Zones\n" +
                "D. Turf War\n" +
                "Answer: D. Turf War\n" +
                "Difficulty: Easy\n" +
                "\n" +
                "\n" +
                "\n" +
                "Q: In which game does the player take control of an avatar and explores the world of Telara to fight creatures and do quests to gain experience and loot?\n" +
                "\n" +
                "A. Overwatch\n" +
                "B. Rift\n" +
                "C. Destiny\n" +
                "D. World of Warcraft\n" +
                "Answer: B. Rift\n" +
                "Difficulty: Medium "
    }
}

@JvmInline
value class Prompt(val value: String)

@JvmInline
value class PromptResponse(val value: String)
