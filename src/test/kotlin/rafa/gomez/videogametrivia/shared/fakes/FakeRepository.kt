package rafa.gomez.videogametrivia.shared.fakes

interface FakeRepository<ID, T> {
    val elements: MutableMap<ID, T>
    val errors: MutableList<Throwable>

    fun shouldFailWith(error: Throwable) = errors.add(error)

    fun resetFake() {
        elements.clear()
        errors.clear()
    }

    fun contains(resource: T): Boolean = resource in elements.values
    fun contains(vararg resource: T): Boolean = resource.all { it in elements.values }
    fun containsBy(id: ID): Boolean = id in elements.keys

    fun <Response> failIfConfiguredOrElse(block: () -> Response): Response =
        if (errors.isNotEmpty()) throw errors.removeFirst()
        else block()

    fun MutableMap<ID, T>.saveOrUpdate(id: ID, value: T) =
        if (id in elements.keys)
            elements.replace(id, value)
        else elements.put(id, value)
}
