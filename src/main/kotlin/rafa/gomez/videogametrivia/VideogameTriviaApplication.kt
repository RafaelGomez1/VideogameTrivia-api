package rafa.gomez.videogametrivia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import rafa.gomez.videogametrivia.shared.response.Response

@SpringBootApplication
class VideogameTriviaApplication

fun main(args: Array<String>) {
    runApplication<VideogameTriviaApplication>(*args)
}

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): Response<String> =
        if (ex.message?.contains("Invalid UUID") == true) Response.status(NOT_FOUND).body("Identifier is not a UUID")
        else throw ex
}
