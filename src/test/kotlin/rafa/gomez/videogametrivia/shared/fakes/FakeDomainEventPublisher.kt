package rafa.gomez.videogametrivia.shared.fakes

import rafa.gomez.videogametrivia.shared.event.bus.DomainEvent
import rafa.gomez.videogametrivia.shared.event.bus.DomainEventPublisher


object FakeDomainEventPublisher : DomainEventPublisher {
    private val publishedEvents = mutableListOf<DomainEvent>()
    private val errors = mutableListOf<Throwable>()

    override fun publish(events: List<DomainEvent>) {
        if (errors.isNotEmpty()) throw errors.removeFirst()
        else publishedEvents.addAll(events)
    }

    fun published(vararg event: DomainEvent) = publishedEvents.containsAll(event.toList())
    fun noEventsPublished() = publishedEvents.isEmpty()

    fun resetFake() {
        publishedEvents.clear()
        errors.clear()
    }

    fun shouldFailWith(vararg errors: Throwable) = this.errors.addAll(errors)
}
