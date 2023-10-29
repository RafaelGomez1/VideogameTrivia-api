package rafa.gomez.videogametrivia.question.primaryadapter.create

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import rafa.gomez.videogametrivia.question.application.create.QuestionCreator
import rafa.gomez.videogametrivia.challenge.domain.Category.DEVELOPMENT_STUDIOS
import rafa.gomez.videogametrivia.challenge.domain.Category.FUN_FACTS
import rafa.gomez.videogametrivia.question.domain.Difficulty.Easy
import rafa.gomez.videogametrivia.question.domain.Difficulty.Hard
import rafa.gomez.videogametrivia.question.domain.Difficulty.Medium

@Component
class CreateQuestionAutogenerator(private val creator: QuestionCreator) {

    @EventListener(ApplicationReadyEvent::class)
    fun run() {
        runBlocking {
            launch {
                val job1 = async {
                    repeat(200) {
                        creator.invoke(FUN_FACTS, Easy, 1)
                        delay(100)
                    }
                    repeat(200) {
                        creator.invoke(FUN_FACTS, Hard, 1)
                        delay(100)
                    }
                    repeat(200) {
                        creator.invoke(FUN_FACTS, Medium, 1)
                        delay(100)
                    }
                }
                val job2 = async {
                    repeat(200) {
                        creator.invoke(DEVELOPMENT_STUDIOS, Easy, 1)
                        delay(100)
                    }
                    repeat(200) {
                        creator.invoke(DEVELOPMENT_STUDIOS, Hard, 1)
                        delay(100)
                    }
                    repeat(200) {
                        creator.invoke(DEVELOPMENT_STUDIOS, Medium, 1)
                        delay(100)
                    }
                }

                job1.await()
                job2.await()
            }
        }
    }
}
