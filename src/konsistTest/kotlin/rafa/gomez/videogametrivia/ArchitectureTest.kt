package rafa.gomez.videogametrivia

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withSealedModifier
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

class ArchitectureTest {

    @Test
    fun `application services should have single 'public operator' method named 'invoke'`() {
        Konsist
            .scopeFromPackage("..application..")
            .classes()
            .filter { it.isNotCQRSClass() }
            .assertTrue {
                val hasSingleInvokeOperatorMethod = it.hasFunction { function ->
                    function.name == "invoke" && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                }

                hasSingleInvokeOperatorMethod && it.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1
            }
    }

    @Test
    fun `every sealed class in application has surname 'error'`() {
        Konsist
            .scopeFromPackage("..application..")
            .classes()
            .withSealedModifier()
            .assertTrue { "Error" in it.name }
    }

    @Test
    fun `domain ports must not use arrow`() {
        Konsist
            .scopeFromPackage("..domain..")
            .interfaces()
            .assertTrue { port ->
                port.hasAllFunctions { function ->
                    function.returnType?.hasNameContaining("Either<")?.not() ?: true
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

    private companion object {
        const val CQRS_SUFFIX = "Handler"
        const val COMMAND_SUFFIX = "Command"
        const val QUERY_SUFFIX = "Query"
    }
}
