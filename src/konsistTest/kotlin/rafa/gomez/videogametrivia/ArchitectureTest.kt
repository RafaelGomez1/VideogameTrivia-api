package rafa.gomez.videogametrivia

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withSealedModifier
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import rafa.gomez.videogametrivia.SharedConcepts.APPLICATION_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.APPLICATION_SERVICE_METHOD_NAME
import rafa.gomez.videogametrivia.SharedConcepts.COMMAND_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.CQRS_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.DOMAIN_LAYER
import rafa.gomez.videogametrivia.SharedConcepts.EITHER_TYPE
import rafa.gomez.videogametrivia.SharedConcepts.QUERY_SUFFIX
import rafa.gomez.videogametrivia.SharedConcepts.SEALED_CLASS_ERROR_SUFFIX

class ArchitectureTest {

    @Test
    fun `application services should have single 'public operator' method named 'invoke'`() {
        Konsist
            .scopeFromPackage(APPLICATION_LAYER)
            .classes()
            .filter { it.isNotCQRSClass() }
            .assertTrue {
                val hasSingleInvokeOperatorMethod = it.hasFunction { function ->
                    function.name == APPLICATION_SERVICE_METHOD_NAME && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                }

                hasSingleInvokeOperatorMethod && it.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1
            }
    }

    @Test
    fun `every sealed class in application has surname 'error'`() {
        Konsist
            .scopeFromPackage(APPLICATION_LAYER)
            .classes()
            .withSealedModifier()
            .assertTrue { SEALED_CLASS_ERROR_SUFFIX in it.name }
    }

    @Test
    fun `domain ports must not use arrow`() {
        Konsist
            .scopeFromPackage(DOMAIN_LAYER)
            .interfaces()
            .assertTrue { port ->
                port.hasAllFunctions { function ->
                    function.returnType?.hasNameContaining(EITHER_TYPE)?.not() ?: true
                }
            }
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

    private fun KoClassDeclaration.isNotCQRSClass(): Boolean =
        !name.contains(CQRS_SUFFIX) && !name.contains(COMMAND_SUFFIX) && !name.contains(QUERY_SUFFIX)

}
