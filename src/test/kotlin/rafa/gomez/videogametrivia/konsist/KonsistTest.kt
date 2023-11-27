package rafa.gomez.videogametrivia.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withSealedModifier
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withValueModifier
import com.lemonappdev.konsist.api.ext.list.primaryConstructors
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.ext.provider.hasAnnotationOf
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RestController
import rafa.gomez.videogametrivia.shared.response.Response

class KonsistTest {

    @Test
    fun `no empty files allowed`() {
        Konsist.scopeFromProject()
            .files
            .assertFalse { file -> file.text.isEmpty() }
    }

    @Test
    fun `no class should use field injection`() {
        Konsist
            .scopeFromProject()
            .classes()
            .properties()
            .assertFalse { it.hasAnnotationOf<Autowired>() }
    }

    @Test
    fun `no wildcard imports allowed`() {
        Konsist
            .scopeFromProject()
            .imports
            .assertFalse { it.isWildcard }
    }

    @Test
    fun `interfaces with 'Repository' annotation should have 'Repository' suffix`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withAnnotationOf(Repository::class)
            .assertTrue { it.hasNameEndingWith("Repository") }
    }

    @Test
    fun `classes with 'RestController' annotation should have 'Controller' suffix`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(RestController::class)
            .assertTrue {
                it.hasNameEndingWith("Controller")
            }
    }

    @Test
    fun `classes with 'RestController' annotation should reside in 'primaryadapter' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(RestController::class)
            .assertTrue { it.resideInPackage("..primaryadapter..") }
    }

    @Test
    fun `classes with 'RestController' annotation should always return Response`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(RestController::class)
            .functions()
            .assertTrue { function -> function.hasReturnType { it.hasNameStartingWith("Response<") } }
    }

    @Test
    fun `application services should have single 'public operator' method named 'invoke'`() {
        Konsist
            .scopeFromPackage("..application..")
            .classes()
            .assertTrue {
                val hasSingleInvokeOperatorMethod = it.hasFunction { function ->
                    function.name == "invoke" && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                }

                hasSingleInvokeOperatorMethod && it.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1
            }
    }

    @Test
    fun `interfaces with 'Repository' annotation should reside in 'data' package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withAnnotationOf(Repository::class)
            .assertTrue { it.resideInPackage("database") }
    }

    @Test
    fun `every value class has parameter named 'value'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withValueModifier()
            .primaryConstructors
            .assertTrue { it.hasParameterWithName("value") }
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
    fun `package name must match file path`() {
        Konsist
            .scopeFromProject()
            .packages
            .assertTrue { it.hasMatchingPath }
    }
}
