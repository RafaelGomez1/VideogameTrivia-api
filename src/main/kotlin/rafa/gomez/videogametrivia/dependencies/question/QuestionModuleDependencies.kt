package rafa.gomez.videogametrivia.dependencies.question

import io.github.flashvayne.chatgpt.service.ChatgptService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rafa.gomez.videogametrivia.question.application.create.QuestionCreator
import rafa.gomez.videogametrivia.question.domain.QuestionGenerator
import rafa.gomez.videogametrivia.question.domain.QuestionRepository
import rafa.gomez.videogametrivia.question.secondaryadapter.database.JpaQuestionRepository
import rafa.gomez.videogametrivia.question.secondaryadapter.database.MongoQuestionRepository
import rafa.gomez.videogametrivia.question.secondaryadapter.gpt.ChatGPTQuestionGenerator

@Configuration
class QuestionModuleDependencies {

    @Bean fun questionRepository(repository: JpaQuestionRepository): QuestionRepository = MongoQuestionRepository(repository)
    @Bean fun questionGenerator(gpt: ChatgptService): QuestionGenerator = ChatGPTQuestionGenerator(gpt)

    @Bean
    fun questionCreator(repository: QuestionRepository, generator: QuestionGenerator): QuestionCreator = QuestionCreator(repository, generator)
}
