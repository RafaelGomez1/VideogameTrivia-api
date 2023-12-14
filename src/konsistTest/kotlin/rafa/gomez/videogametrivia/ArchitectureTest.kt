package rafa.gomez.videogametrivia

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withSealedModifier
import com.lemonappdev.konsist.api.ext.provider.hasAnnotationOf
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import java.util.stream.Stream
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import rafa.gomez.videogametrivia.SharedConcepts.APPLICATION_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.APPLICATION_SERVICE_METHOD_NAME
import rafa.gomez.videogametrivia.SharedConcepts.COMMAND_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.CQRS_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.DOMAIN_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.EITHER_TYPE
import rafa.gomez.videogametrivia.SharedConcepts.PRIMARY_ADAPTER_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.QUERY_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.SEALED_CLASS_ERROR_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.SEALED_CLASS_UNKNOWN_ERROR
import rafa.gomez.videogametrivia.SharedConcepts.SECONDARY_ADAPTER_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.isApplicationService

class ArchitectureTest {

    @TestFactory
    fun `application services should have single 'public operator' method named 'invoke'`(): Stream<DynamicTest> =
        Konsist
            .scopeFromPackage(APPLICATION_LAYER)
            .classes()
            .filter { it.isApplicationService() }
            .stream()
            .flatMap { applicationService ->
                Stream.of(
                    dynamicTest("${applicationService.name}'s name must have operator invoke function") {
                        applicationService.assertTrue {
                            it.hasFunction { function ->
                                function.name == APPLICATION_SERVICE_METHOD_NAME && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                            }
                        }
                    },

                    dynamicTest("${applicationService.name}'s must have only one public function") {
                        applicationService.assertTrue { it.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1 }
                    }
                )
            }

    @TestFactory
    fun `every sealed class in application has suffix 'Error'`(): Stream<DynamicTest> =
        Konsist
            .scopeFromPackage(APPLICATION_LAYER)
            .classes()
            .withSealedModifier()
            .stream()
            .flatMap { useCaseError ->
                Stream.of(
                    dynamicTest("${useCaseError.name}'s name should end with Error") {
                        useCaseError.assertTrue { SEALED_CLASS_ERROR_SUFFIX in useCaseError.name }
                    },

                    dynamicTest("${useCaseError.name} should not have an Unknown error") {
                        val doesNotHaveUnknownChildClass = !useCaseError.hasClass { it.hasNameContaining(SEALED_CLASS_UNKNOWN_ERROR) }
                        val doesNotHaveUnknownChildObject = !useCaseError.hasObject { it.hasNameContaining(SEALED_CLASS_UNKNOWN_ERROR) }
                        useCaseError.assertTrue { doesNotHaveUnknownChildClass && doesNotHaveUnknownChildObject}
                    }
                )
            }

    @TestFactory
    fun `domain ports must not use arrow`(): Stream<DynamicTest> =
        Konsist
            .scopeFromPackage(DOMAIN_LAYER)
            .interfaces()
            .stream()
            .flatMap { port ->
                Stream.of(
                    dynamicTest("${port.name} should not use arrow") {
                        port.hasAllFunctions { function ->
                            function.returnType?.hasNameContaining(EITHER_TYPE)?.not() ?: true
                        }
                    }
                )
            }

    @Test
    fun `hexagonal architecture layers have correct dependencies for challenge module`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                // Define layers
                val domain = Layer("Domain", "rafa.gomez.videogametrivia.challenge.domain..")
                val application = Layer("Application", "rafa.gomez.videogametrivia.challenge.application..")
                val primaryAdapter = Layer("PrimaryAdapter", "rafa.gomez.videogametrivia.challenge.primaryadapter..")
                val secondaryAdapter = Layer("SecondaryAdapter", "rafa.gomez.videogametrivia.challenge.secondaryadapter..")

                // Can not use things from anywhere else
                domain.dependsOnNothing()

                // Can use concepts from domain
                application.dependsOn(domain)
                secondaryAdapter.dependsOn(domain)

                // Can use concepts from domain and application
                primaryAdapter.dependsOn(domain, application)
            }
    }
}
