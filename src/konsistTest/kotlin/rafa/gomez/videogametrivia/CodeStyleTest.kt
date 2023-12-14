package rafa.gomez.videogametrivia

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withValueModifier
import com.lemonappdev.konsist.api.ext.list.primaryConstructors
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import rafa.gomez.videogametrivia.SharedConcepts.VALUE_CLASS_PROPERTY_NAME

class CodeStyleTest {

    @Test
    fun `no empty files allowed`() {
        Konsist.scopeFromProject()
            .files
            .assertFalse { file -> file.text.isEmpty() }
    }

    @Test
    fun `no wildcard imports allowed`() {
        Konsist
            .scopeFromProject()
            .imports
            .assertFalse { it.isWildcard }
    }

    @Test
    fun `every value class has parameter named 'value'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withValueModifier()
            .primaryConstructors
            .assertTrue { it.hasParameterWithName(VALUE_CLASS_PROPERTY_NAME) }
    }

    @Test
    fun `package name must match file path`() {
        Konsist
            .scopeFromProject()
            .packages
            .assertTrue { it.hasMatchingPath }
    }
}
