package rafa.gomez.videogametrivia

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.ext.provider.hasAnnotationOf
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RestController
import rafa.gomez.videogametrivia.SharedConcepts.CONTROLLER_ANNOTATION
import rafa.gomez.videogametrivia.SharedConcepts.DATABASE_PACKAGE
import rafa.gomez.videogametrivia.SharedConcepts.PRIMARY_ADAPTER_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.REPOSITORY_ANNOTATION
import rafa.gomez.videogametrivia.SharedConcepts.RESPONSE_TYPE

class SpringTest {

    @Test
    fun `no class should use field injection`() {
        Konsist
            .scopeFromProject()
            .classes()
            .properties()
            .assertFalse { it.hasAnnotationOf<Autowired>() }
    }

    @Test
    fun `interfaces with 'Repository' annotation should have 'Repository' suffix`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withAnnotationOf(Repository::class)
            .assertTrue { it.hasNameEndingWith(REPOSITORY_ANNOTATION) }
    }

    @Test
    fun `classes with 'RestController' annotation should have 'Controller' suffix`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(RestController::class)
            .assertTrue {
                it.hasNameEndingWith(CONTROLLER_ANNOTATION)
            }
    }

    @Test
    fun `classes with 'RestController' annotation should reside in 'primaryadapter' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(RestController::class)
            .assertTrue { it.resideInPackage(PRIMARY_ADAPTER_LAYER) }
    }

    @Test
    fun `classes with 'RestController' annotation should always return Response`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(RestController::class)
            .functions()
            .assertTrue { function -> function.hasReturnType { it.hasNameStartingWith(RESPONSE_TYPE) } }
    }

    @Test
    fun `interfaces with 'Repository' annotation should reside in 'data' package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withAnnotationOf(Repository::class)
            .assertTrue { it.resideInPackage(DATABASE_PACKAGE) }
    }
}
