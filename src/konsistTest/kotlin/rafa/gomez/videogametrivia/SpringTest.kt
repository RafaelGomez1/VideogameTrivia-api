package rafa.gomez.videogametrivia

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.ext.provider.hasAnnotationOf
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import java.util.stream.Stream
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RestController
import rafa.gomez.videogametrivia.SharedConcepts.CONTROLLER_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.DATABASE_PACKAGE
import rafa.gomez.videogametrivia.SharedConcepts.PRIMARY_ADAPTER_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.REPOSITORY_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.RESPONSE_TYPE

class SpringTest {

    @TestFactory
    fun `controller rules`(): Stream<DynamicTest> =
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(RestController::class)
            .stream()
            .flatMap { controller ->
                Stream.of(
                    dynamicTest("${controller.name}'s must have 'Controller' suffix") {
                        controller.assertTrue { it.hasNameEndingWith(CONTROLLER_SUFFIX) }
                    },

                    dynamicTest("${controller.name}'s must reside in 'primaryadapter' package") {
                        controller.assertTrue { it.resideInPackage(PRIMARY_ADAPTER_LAYER) }
                    },

                    dynamicTest("${controller.name}'s must return Response data type") {
                        controller.functions().assertTrue { function -> function.hasReturnType { it.hasNameStartingWith(RESPONSE_TYPE) } }
                    }
                )
            }

    @TestFactory
    fun `no class should use field injection`(): Stream<DynamicTest> =
        Konsist
            .scopeFromProduction()
            .classes()
            .stream()
            .flatMap { classDeclaration ->
                Stream.of(
                    dynamicTest("${classDeclaration.name} should not have properties annotated with Autowired") {
                        classDeclaration.properties().assertFalse { property -> property.hasAnnotationOf<Autowired>() }
                    }
                )
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
